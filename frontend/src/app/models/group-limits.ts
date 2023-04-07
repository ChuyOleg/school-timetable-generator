import { ITimeSlot } from "./time-slot";
import { ISubjectHoursInGroup } from "./subject-hours-in-group";
import { ISubjectTeacherInGroup } from "./subject-teacher-in-group";

export interface IGroupLimits {
  id?: number
  subjectTeacherInGroupDtoSet: ISubjectTeacherInGroup[],
  subjectHoursInGroupDtoSet: ISubjectHoursInGroup[],
  maxHoursPerWeek: number,
  interschoolCombine: ITimeSlot
}
