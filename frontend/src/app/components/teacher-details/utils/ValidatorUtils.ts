import { FreeDayConstraint } from "../model/constraint/FreeDayConstraint";
import { PeriodConstraint } from "../model/constraint/PeriodConstraint";
import { LessonsOrderConstraint } from "../model/constraint/LessonsOrderConstraint";
import { MaxLessonsConstraint } from "../model/constraint/MaxLessonsConstraint";

export class ValidatorUtils {

  static isMaxLessonPerDayLimitValid(constraint: MaxLessonsConstraint): boolean {
    return !constraint.isActive || constraint.count.valid;
  }

  static isLessonsOrderLimitValid(constraint: LessonsOrderConstraint): boolean {
    return constraint.importanceLevel.valid;
  }

  static areFreeDayLimitsValid(constraints: FreeDayConstraint[]): boolean {
    return constraints.every(constraint => constraint.day.valid);
  }

  static areDesiredPeriodLimitsValid(constraints: PeriodConstraint[]): boolean {
    return constraints.every(constraint =>
      constraint.day.valid
      && constraint.shift.valid
      && constraint.lessonFrom.valid
      && constraint.lessonTo
      && ValidatorUtils.areLessonNumbersValid(constraint) );
  }

  private static areLessonNumbersValid(constraint: PeriodConstraint): boolean {
    const lessonFrom = constraint.lessonFrom.value;
    const lessonTo = constraint.lessonTo.value;

    if (lessonFrom == null || lessonTo  == null) return true;

    return lessonTo >= lessonFrom;
  }
}
