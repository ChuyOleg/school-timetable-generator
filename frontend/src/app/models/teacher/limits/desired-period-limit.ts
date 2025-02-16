import { EDayOfWeek } from "../../enumeration/day-of-week";

export interface IDesiredPeriodLimit {
  id?: number
  day: EDayOfWeek
  shift: number
  lessonFrom: number
  lessonTo: number
}
