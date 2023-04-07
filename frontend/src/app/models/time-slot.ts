import { ELessonNumber } from "./enumeration/lesson-number";
import { EDayOfWeek } from "./enumeration/day-of-week";
import { EWeekType } from "./enumeration/week-type";

export interface ITimeSlot {
  id?: null
  lessonNumber: ELessonNumber
  day: EDayOfWeek
  weekType: EWeekType
}
