import { ISubject } from "../../../models/subject";
import { LessonLimitsHolder } from "../model/LessonLimitsHolder";
import { FormControl, FormGroup } from "@angular/forms";
import { LessonLimitsGroup } from "../model/LessonLimitsGroup";
import { FormControlFactory } from "./FormControlFactory";
import { ISubjectLimits } from "../../../models/subject-limits";

export class LessonLimitsFactory {

  static createLessonLimits(
    subject: ISubject,
    lessonConstraints: LessonLimitsHolder[],
    maxHoursPerWeek: FormControl<number | null>): LessonLimitsHolder {

    const baseFormGroup = this.createBaseFormGroup(null, lessonConstraints, maxHoursPerWeek);

    return {
      subject: subject,
      controlGroup: baseFormGroup }
  }

  static createLessonLimitsWithValues(
    subjectLimits: ISubjectLimits,
    lessonConstraints: LessonLimitsHolder[],
    maxHoursPerWeek: FormControl<number | null>): LessonLimitsHolder {

    const baseFormGroup = this.createBaseFormGroup(subjectLimits, lessonConstraints, maxHoursPerWeek);

    if (this.isSubgroupPresent(subjectLimits)) {
      baseFormGroup.addControl("teacher2", FormControlFactory.createTeacherFormControl(subjectLimits.teacherDto2 || null));
      baseFormGroup.addControl("room2", FormControlFactory.createRoomFormControl(subjectLimits.roomDto2 || null))
    }

    return {
      id: subjectLimits.id,
      subject: subjectLimits.subjectDto,
      controlGroup: baseFormGroup };
  }

  private static createBaseFormGroup(
    subjectLimits: ISubjectLimits | null,
    lessonConstraints: LessonLimitsHolder[],
    maxHoursPerWeek: FormControl<number | null>): FormGroup<LessonLimitsGroup> {

    return new FormGroup<LessonLimitsGroup>({
      hours: FormControlFactory.createHoursFormControl(subjectLimits?.hours || null, lessonConstraints, maxHoursPerWeek),
      teacher: FormControlFactory.createTeacherFormControl(subjectLimits?.teacherDto || null),
      room: FormControlFactory.createRoomFormControl(subjectLimits?.roomDto || null) })
  }

  private static isSubgroupPresent(subjectLimits: ISubjectLimits): boolean {
    return subjectLimits.teacherDto2 != null;
  }
}
