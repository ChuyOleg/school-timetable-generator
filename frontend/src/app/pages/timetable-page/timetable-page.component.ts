import { Component, OnInit } from '@angular/core';
import { TeacherService } from "../../services/teacher/teacher.service";
import { delay, firstValueFrom } from "rxjs";
import { ITeacherInvalidLoading } from "../../models/util/teacher-invalid-loading";
import { GroupService } from "../../services/group/group.service";
import { ILessonLightweight } from "../../models/util/lesson-lightweight";
import { ITeacher } from "../../models/teacher";
import { SubjectService } from "../../services/subject/subject.service";
import { ISubject } from "../../models/subject";
import { IGroup } from "../../models/group";
import { TimeslotService } from "../../services/timeslot/timeslot.service";
import { ITimeSlot } from "../../models/time-slot";
import { KeyValue } from "@angular/common";
import { TimetableService } from "../../services/timetable/timetable.service";
import { DialogService } from "../../services/dialog.service";

@Component({
  selector: 'app-timetable-page',
  templateUrl: './timetable-page.component.html'
})
export class TimetablePageComponent implements OnInit {

  public timetableByGroups: Map<number, ILessonLightweight[]> = new Map()
  public teachersById: Record<number, ITeacher> = []
  public subjectsById: Record<number, ISubject> = []
  public timeslotsById: Record<number, ITimeSlot> = []
  public groupsById: Record<number, IGroup> = []
  public teachersWithInvalidLoading: ITeacherInvalidLoading[] = []

  public days: string[] = ['Понеділок', 'Вівторок', 'Середа', 'Четвер', 'П`ятниця']
  nums: number[] = Array.from({length: 8}, (_, i) => i + 1);
  public viewState: boolean = false
  public generateButtonIsClicked: boolean = false;
  public loading: boolean = false
  public isTimetablePresent: boolean = false

  constructor(
    private teacherService: TeacherService,
    private subjectService: SubjectService,
    private timeslotService: TimeslotService,
    private groupService: GroupService,
    public timetableService: TimetableService,
    private dialogService: DialogService
  ) {
  }

  async ngOnInit() {
    await this.checkTeachersLoading();
    if (this.teachersWithInvalidLoading.length === 0) {
      this.loading = true;

      this.timetableService.getForUser().pipe(delay(500)).subscribe(async timetable => {
        if (timetable) {
          await this.loadExtraData();
          timetable.lessons.forEach(lesson => {
            if (this.timetableByGroups.get(lesson.groupId) == null) {
              this.timetableByGroups.set(lesson.groupId, [lesson]);
            } else {
              this.timetableByGroups.get(lesson.groupId)?.push(lesson)
            }
          })

          this.isTimetablePresent = true
        } else {
          this.isTimetablePresent = false;
        }

        this.loading = false;
      })
    }
  }

  async generate() {
    this.generateButtonIsClicked = true;
    await this.checkTeachersLoading();
    if (this.teachersWithInvalidLoading.length === 0) {
      this.loading = true;
      await this.loadExtraData();
      this.timetableService.generate().subscribe(timetable => {
        timetable.lessons.forEach(lesson => {
          if (this.timetableByGroups.get(lesson.groupId) == null) {
            this.timetableByGroups.set(lesson.groupId, [lesson]);
          } else {
            this.timetableByGroups.get(lesson.groupId)?.push(lesson)
          }
        })

        this.loading = false;
        this.isTimetablePresent = true
      });
    }
    this.generateButtonIsClicked = false;
  }

  submit() {
    if (this.timetableService.timetable?.id) {
      this.update();
    } else {
      this.save();
    }
  }

  save() {
    if (this.timetableService.timetable) {
      this.timetableService.save(this.timetableService.timetable).subscribe();
    }
  }

  update() {
    if (this.timetableService.timetable) {
      this.timetableService.update(this.timetableService.timetable).subscribe();
    }
  }

  delete() {
    this.dialogService.confirmDialog({
      title: "Видалення розкладу",
      message: `Ви дійсно хочете видалити розклад?`,
      cancelText: "Ні",
      confirmText: "Так"
    }).subscribe(bool => {
      if (this.timetableService.timetable && this.timetableService.timetable.id && bool) {
        this.timetableService.delete(this.timetableService.timetable?.id).subscribe(() => {
          this.isTimetablePresent = false;
        });
      }
    })
  }

  private async checkTeachersLoading() {
    await firstValueFrom(this.teacherService.getAll());

    const teachersActualHours = await firstValueFrom(this.teacherService.getTeachersHours());
    this.teacherService.teachers.forEach(teacher => {
      const actualHours = teachersActualHours.find(t => t.id === teacher.id)?.totalHours;
      if (teacher.maxHoursPerWeek != actualHours) {
        this.teachersWithInvalidLoading.push({
          name: teacher.name,
          expectedHours: teacher.maxHoursPerWeek,
          actualHours: actualHours as number
        })
      }
    });
  }

  private async loadExtraData() {
    await firstValueFrom(this.subjectService.getAll());
    await firstValueFrom(this.timeslotService.getAll());
    await firstValueFrom(this.groupService.getAll());

    this.teacherService.teachers.forEach(teacher => {
      if (teacher.id) this.teachersById[teacher.id] = teacher
    });
    this.subjectService.subjects.forEach(subject => {
      if (subject.id) this.subjectsById[subject.id] = subject
    });
    this.timeslotService.timeslots.forEach(timeslot => {
      if (timeslot.id) this.timeslotsById[timeslot.id] = timeslot
    });
    this.groupService.groups.forEach(group => {
      if (group.id) this.groupsById[group.id] = group
    });
  }

  sortByGradeNumberAndLetter = (
    a: KeyValue<number, ILessonLightweight[]>,
    b: KeyValue<number, ILessonLightweight[]>
  ): number => {
    const group1 = this.groupsById[a.key];
    const group2 = this.groupsById[b.key];
    if (group1.gradeNumber === group2.gradeNumber) {
      return group1.letter.toLowerCase().localeCompare(group2.letter.toLowerCase());
    }
    return group1.gradeNumber - group2.gradeNumber;
  };

  toggleViewState() {
    this.viewState = !this.viewState;
  }

}
