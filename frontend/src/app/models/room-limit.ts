import { EDayOfWeek } from "./enumeration/day-of-week";

export interface IRoomLimit {
  id?: number
  day: EDayOfWeek
  lessonNumberFrom: number
  lessonNumberTo: number
}
