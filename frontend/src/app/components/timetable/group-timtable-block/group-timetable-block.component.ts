import { Component, Input, OnChanges } from '@angular/core';
import { ILessonLightweight } from "../../../models/util/lesson-lightweight";
import { ITimeSlot } from "../../../models/time-slot";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { IGroup } from "../../../models/group";
import { ISubject } from "../../../models/subject";
import { EWeekType } from "../../../models/enumeration/week-type";
import { ITeacher } from "../../../models/teacher";
import { ILessonInTimetableComplexInfo } from "../../../models/util/lesson-in-timetable-complex-info";
import { TimetableService } from "../../../services/timetable/timetable.service";
import { ILesson } from "../../../models/lesson";

@Component({
  selector: 'app-group-timetable-block',
  templateUrl: './group-timetable-block.component.html'
})
export class GroupTimetableBlockComponent implements OnChanges {

  @Input() lessons: ILessonLightweight[]
  @Input() subjectsById: Record<number, ISubject>
  @Input() teachersById: Record<number, ITeacher>
  @Input() timeslotsById: Record<number, ITimeSlot>
  @Input() group: IGroup
  @Input() viewState: boolean

  public mondayLessons: Record<number, ILessonInTimetableComplexInfo> = []
  public tuesdayLessons: Record<number, ILessonInTimetableComplexInfo> = []
  public wednesdayLessons: Record<number, ILessonInTimetableComplexInfo> = []
  public thursdayLessons: Record<number, ILessonInTimetableComplexInfo> = []
  public fridayLessons: Record<number, ILessonInTimetableComplexInfo> = []

  isLoaded: boolean = false
  days: EDayOfWeek[] = [EDayOfWeek.MONDAY, EDayOfWeek.TUESDAY, EDayOfWeek.WEDNESDAY, EDayOfWeek.THURSDAY, EDayOfWeek.FRIDAY]
  nums: number[] = Array.from({length: 8}, (_, i) => i + 1);

  constructor(
    private timetableService: TimetableService
  ) {}

  ngOnChanges() {
    if (this.areAllInputsLoaded() && !this.isLoaded) {
      this.lessons.forEach(lesson => {
        const timeSlot = this.timeslotsById[lesson.timeSlotId];
        const dayLessons = this.getDayLessonsByDay(timeSlot.day);

        if (dayLessons[timeSlot.lessonNumber]) {
          this.updateValueInDayLessons(timeSlot, lesson, dayLessons);
        } else {
          this.insertIntoDayLessonsNewValue(timeSlot, lesson, dayLessons);
        }
      })
      this.isLoaded = true;
    }
  }

  private insertIntoDayLessonsNewValue(
    timeSlot: ITimeSlot, lesson: ILessonLightweight,
    dayLessons: Record<number, ILessonInTimetableComplexInfo>
  ) {
    switch (timeSlot.weekType) {
      case EWeekType.BOTH: {
        dayLessons[timeSlot.lessonNumber] = {lesson1: lesson}
        break
      }
      case EWeekType.EVEN: {
        dayLessons[timeSlot.lessonNumber] = {evenLesson: lesson}
        break;
      }
      case EWeekType.ODD: {
        dayLessons[timeSlot.lessonNumber] = {oddLesson: lesson}
        break;
      }
    }
  }

  private updateValueInDayLessons(
    timeSlot: ITimeSlot, lesson: ILessonLightweight,
    dayLessons: Record<number, ILessonInTimetableComplexInfo>
  ) {
    switch (timeSlot.weekType) {
      case EWeekType.BOTH: {
        dayLessons[timeSlot.lessonNumber].lesson2 = lesson
        break
      }
      case EWeekType.EVEN: {
        dayLessons[timeSlot.lessonNumber].evenLesson = lesson
        break;
      }
      case EWeekType.ODD: {
        dayLessons[timeSlot.lessonNumber].oddLesson = lesson
        break;
      }
    }
  }

  isInterschoolCombine(day: EDayOfWeek, lessonNumber: number): boolean {
    if (this.group.groupLimitsDto?.interschoolCombine) {
      const combine = this.group.groupLimitsDto?.interschoolCombine;
      if (combine?.day === day && combine.lessonNumber === lessonNumber) {
        return true;
      }
    }
    return false;
  }

  isLesson1Present(day: EDayOfWeek, lessonNumber: number): boolean {
    return this.getDayLessonsByDay(day)[lessonNumber].lesson1 != null;
  }

  isLesson2Present(day: EDayOfWeek, lessonNumber: number): boolean {
    return this.getDayLessonsByDay(day)[lessonNumber].lesson2 != null;
  }

  getLesson1SubjectName(day: EDayOfWeek, lessonNumber: number): string {
    const lesson1 = this.getDayLessonsByDay(day)[lessonNumber].lesson1;
    return lesson1 ? this.subjectsById[lesson1.subjectId].name : 'Error';
  }

  getLesson1TeacherName(day: EDayOfWeek, lessonNumber: number): string {
    const lesson1 = this.getDayLessonsByDay(day)[lessonNumber].lesson1;
    return lesson1 ? this.teachersById[lesson1.teacherId].name : 'Error';
  }

  getLesson2TeacherName(day: EDayOfWeek, lessonNumber: number): string {
    const lesson2 = this.getDayLessonsByDay(day)[lessonNumber].lesson2;
    return lesson2 ? this.teachersById[lesson2.teacherId].name : 'Error';
  }

  getEvenLessonSubjectName(day: EDayOfWeek, lessonNumber: number): string {
    const evenLesson = this.getDayLessonsByDay(day)[lessonNumber].evenLesson;
    return evenLesson ? this.subjectsById[evenLesson.subjectId].name : 'Error';
  }

  getEvenLessonTeacherName(day: EDayOfWeek, lessonNumber: number): string {
    const evenLesson = this.getDayLessonsByDay(day)[lessonNumber].evenLesson;
    return evenLesson ? this.teachersById[evenLesson.teacherId].name : 'Error';
  }

  getOddLessonSubjectName(day: EDayOfWeek, lessonNumber: number): string {
    const oddLesson = this.getDayLessonsByDay(day)[lessonNumber].oddLesson;
    return oddLesson ? this.subjectsById[oddLesson.subjectId].name : 'Error';
  }

  getOddLessonTeacherName(day: EDayOfWeek, lessonNumber: number): string {
    const oddLesson = this.getDayLessonsByDay(day)[lessonNumber].oddLesson;
    return oddLesson ? this.teachersById[oddLesson.teacherId].name : 'Error';
  }

  hasLesson(day: EDayOfWeek, lessonNumber: number): boolean {
    return this.getDayLessonsByDay(day)[lessonNumber] != null;
  }

  isFriday(day: EDayOfWeek): boolean {
    return day === EDayOfWeek.FRIDAY;
  }

  lesson1HasFine(day: EDayOfWeek, lessonNumber: number): boolean {
    const lesson = this.getDayLessonsByDay(day)[lessonNumber].lesson1

    if (lesson) {
      return (this.timetableService.timetableFines?.teacherFines.find(l => this.areEqual(lesson, l)) != null);
    }

    return false;
  }

  lesson2HasFine(day: EDayOfWeek, lessonNumber: number): boolean {
    const lesson = this.getDayLessonsByDay(day)[lessonNumber].lesson2

    if (lesson) {
      return (this.timetableService.timetableFines?.teacherFines.find(l => this.areEqual(lesson, l)) != null);
    }

    return false;
  }

  evenLessonHasFine(day: EDayOfWeek, lessonNumber: number): boolean {
    const lesson = this.getDayLessonsByDay(day)[lessonNumber].evenLesson

    if (lesson) {
      return (this.timetableService.timetableFines?.teacherFines.find(l => this.areEqual(lesson, l)) != null);
    }

    return false;
  }

  oddLessonHasFine(day: EDayOfWeek, lessonNumber: number): boolean {
    const lesson = this.getDayLessonsByDay(day)[lessonNumber].oddLesson

    if (lesson) {
      return (this.timetableService.timetableFines?.teacherFines.find(l => this.areEqual(lesson, l)) != null);
    }

    return false;
  }


  private areEqual(lesson1: ILessonLightweight, lesson2: ILesson): boolean {
    return (lesson1.id && lesson2.id && lesson1?.id === lesson2.id) ||
      (
        lesson1?.groupId === lesson2.groupDto.id && lesson1?.teacherId === lesson2.teacherDto.id &&
        lesson1?.subjectId === lesson2.subjectDto.id && (lesson1?.roomId === (lesson2.roomDto?.id || null)) &&
        lesson1?.timeSlotId === lesson2.timeSlotDto.id
      );
  }

  private getDayLessonsByDay(day: EDayOfWeek): Record<number, ILessonInTimetableComplexInfo> {
    switch (day) {
      case EDayOfWeek.MONDAY: return this.mondayLessons;
      case EDayOfWeek.TUESDAY: return this.tuesdayLessons;
      case EDayOfWeek.WEDNESDAY: return this.wednesdayLessons;
      case EDayOfWeek.THURSDAY: return this.thursdayLessons;
      case EDayOfWeek.FRIDAY: return this.fridayLessons;
    }
  }

  private areAllInputsLoaded(): boolean {
    return (this.lessons && this.subjectsById && this.teachersById && this.timeslotsById && this.group) != null;
  }

}
