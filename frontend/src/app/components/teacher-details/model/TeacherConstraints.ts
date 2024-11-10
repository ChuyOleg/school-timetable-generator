import { FreeDayConstraint } from "./constraint/FreeDayConstraint";
import { PeriodConstraint } from "./constraint/PeriodConstraint";
import { MaxLessonsConstraint } from "./constraint/MaxLessonsConstraint";
import { LessonsOrderConstraint } from "./constraint/LessonsOrderConstraint";

export interface TeacherConstraints {
  id: number
  freeDayConstraints: FreeDayConstraint[]
  desiredPeriodConstraints: PeriodConstraint[]
  maxLessonPerDayConstraint: MaxLessonsConstraint
  lessonsOrderConstraint: LessonsOrderConstraint
}
