import { ITimeSlot } from "./time-slot";
import { ISubjectLimits } from "./subject-limits";

export interface IGroupLimits {
  id?: number
  subjectLimitsDtoSet: ISubjectLimits[],
  maxHoursPerWeek: number,
  interschoolCombine?: ITimeSlot
}
