import { ITimeSlot } from "./time-slot";
import { ISubjectHoursInGroup } from "./subject-hours-in-group";
import { ISubjectTeacherRoomInGroup } from "./subject-teacher-room-in-group";

export interface IGroupLimits {
  id?: number
  subjectTeacherRoomInGroupDtoSet: ISubjectTeacherRoomInGroup[],
  subjectHoursInGroupDtoSet: ISubjectHoursInGroup[],
  maxHoursPerWeek: number,
  interschoolCombine: ITimeSlot
}
