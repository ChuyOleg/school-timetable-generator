import { FormControl } from "@angular/forms";
import { ISubjectLimits } from "../../../models/subject-limits";
import { ITeacher } from "../../../models/teacher";
import { IRoom } from "../../../models/room";
import { ISubject } from "../../../models/subject";
import { IGroup } from "../../../models/group";
import { ITimeSlot } from "../../../models/time-slot";
import { EWeekType } from "../../../models/enumeration/week-type";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { GroupLimitsPageState } from "../model/GroupLimitsPageState";
import { LessonLimitsHolder } from "../model/LessonLimitsHolder";
import { InterschoolCombine } from "../model/InterschoolCombine";

export class Utils {

  static calculateHoursSumAndSetIt(
    lessonDetails: LessonLimitsHolder[],
    maxHoursPerWeekForm: FormControl<number | null>): void {

    let sum: number = 0;

    lessonDetails.forEach(lesDet => {
      const hoursForm = lesDet.controlGroup?.get("hours") as FormControl<number | null>
      if (hoursForm.valid) {
        sum += Number(hoursForm.value);
      }
    })

    maxHoursPerWeekForm.setValue(sum);
  }

  static buildInterschoolCombineInstance(
    pageState: GroupLimitsPageState,
    interschoolCombine: InterschoolCombine): ITimeSlot | undefined {

    if (!pageState.isInterschoolCombinePicked) {
      return undefined;
    }

    return {
      weekType: EWeekType.BOTH,
      day: interschoolCombine.day.value as EDayOfWeek,
      lessonNumber:interschoolCombine.lessonNumber.value as number };
  }


  static buildLessonLimitsNew(lessonConstraints: LessonLimitsHolder[], group: IGroup): ISubjectLimits[] {
    return lessonConstraints
      .map(lessonConstraint => {
        return {
          id: lessonConstraint.id ?? this.findPossibleSubjectHoursId(lessonConstraint.subject, group),
          subjectDto: lessonConstraint.subject,
          hours: lessonConstraint.controlGroup.controls.hours.value as number,
          teacherDto: lessonConstraint.controlGroup.controls.teacher.value as ITeacher,
          roomDto: lessonConstraint.controlGroup.controls.room.value as IRoom,
          teacherDto2: lessonConstraint.controlGroup.controls.teacher2?.value as ITeacher,
          roomDto2: lessonConstraint.controlGroup.controls.room2?.value as IRoom
        }
      })
  }

  private static findPossibleSubjectHoursId(subjectDto: ISubject, group: IGroup): number | undefined {
    return group.groupLimitsDto?.subjectLimitsDtoSet.find(dto => dto.subjectDto.id == subjectDto.id)?.id;
  }
}
