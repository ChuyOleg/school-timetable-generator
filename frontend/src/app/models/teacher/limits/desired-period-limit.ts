import { EDayOfWeek } from "../../enumeration/day-of-week";

export interface IDesiredPeriodLimit {
  id?: number
  day: EDayOfWeek
  lessonFrom: number
  lessonTo: number
}
