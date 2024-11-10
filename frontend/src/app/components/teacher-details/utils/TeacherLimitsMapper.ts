import { ITeacherLimits } from "../../../models/teacher/teacher-limits";
import { TeacherConstraints } from "../model/TeacherConstraints";
import { IFreeDayLimit } from "../../../models/teacher/limits/free-day-limit";
import { FreeDayConstraint } from "../model/constraint/FreeDayConstraint";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { PeriodConstraint } from "../model/constraint/PeriodConstraint";
import { IDesiredPeriodLimit } from "../../../models/teacher/limits/desired-period-limit";
import { IMaxLessonsLimit } from "../../../models/teacher/limits/max-lessons-limit";
import { MaxLessonsConstraint } from "../model/constraint/MaxLessonsConstraint";
import { LessonsOrderConstraint } from "../model/constraint/LessonsOrderConstraint";
import { ILessonsOrderLimit } from "../../../models/teacher/limits/lessons-order-limit";
import { EImportanceLevel } from "../../../models/enumeration/importance-level";

export class TeacherLimitsMapper {

  static map(constraints: TeacherConstraints): ITeacherLimits {
    return {
      id: constraints.id,
      freeDayLimits: TeacherLimitsMapper.toFreeDayLimits(constraints.freeDayConstraints),
      desiredPeriodLimits: TeacherLimitsMapper.toDesiredPeriodLimits(constraints.desiredPeriodConstraints),
      maxLessonsLimit: TeacherLimitsMapper.toMaxLessonsLimit(constraints.maxLessonPerDayConstraint),
      lessonsOrderLimit: TeacherLimitsMapper.toLessonsOrderLimit(constraints.lessonsOrderConstraint) };
  }

  private static toFreeDayLimits(constraints: FreeDayConstraint[]): IFreeDayLimit[] {
    return constraints.map(constraint => TeacherLimitsMapper.toFreeDayLimit(constraint));
  }

  private static toFreeDayLimit(constraint: FreeDayConstraint): IFreeDayLimit {
    return {
      id: constraint.id,
      day: constraint.day.value as EDayOfWeek };
  }

  private static toDesiredPeriodLimits(constraints: PeriodConstraint[]): IDesiredPeriodLimit[] {
    return constraints.map(constraint => TeacherLimitsMapper.toDesiredPeriodLimit(constraint));
  }

  private static toDesiredPeriodLimit(constraint: PeriodConstraint): IDesiredPeriodLimit {
    return {
      id: constraint.id,
      day: constraint.day.value as EDayOfWeek,
      lessonFrom: constraint.lessonFrom.value as number,
      lessonTo: constraint.lessonTo.value as number };
  }

  private static toMaxLessonsLimit(constraint: MaxLessonsConstraint): IMaxLessonsLimit | undefined {
    if (!constraint.isActive) return undefined;

    return {
      id: constraint.id,
      count: constraint.count.value as number };
  }

  private static toLessonsOrderLimit(constraint: LessonsOrderConstraint): ILessonsOrderLimit {
    return {
      id: constraint.id,
      importanceLevel: constraint.importanceLevel.value as EImportanceLevel };
  }
}
