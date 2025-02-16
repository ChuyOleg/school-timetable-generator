import { EDayOfWeek } from "./enumeration/day-of-week";

export interface IRoomLimit {
  id?: number
  day: EDayOfWeek
  shift: number
  lessonNumberFrom: number
  lessonNumberTo: number
}
