import { EDayOfWeek } from "./enumeration/day-of-week";
import { EWeekType } from "./enumeration/week-type";

export interface ITimeSlot {
  id?: null
  lessonNumber: number
  day: EDayOfWeek
  weekType: EWeekType
}
