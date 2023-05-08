import { EDayOfWeek } from "./enumeration/day-of-week";
import { EWeekType } from "./enumeration/week-type";

export interface ITimeSlot {
  id?: number
  lessonNumber: number
  day: EDayOfWeek
  weekType: EWeekType
}
