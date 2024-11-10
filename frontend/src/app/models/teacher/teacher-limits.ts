import { IFreeDayLimit } from "./limits/free-day-limit";
import { ILessonsOrderLimit } from "./limits/lessons-order-limit";
import { IMaxLessonsLimit } from "./limits/max-lessons-limit";
import { IDesiredPeriodLimit } from "./limits/desired-period-limit";

export interface ITeacherLimits {
  id?: number
  freeDayLimits: IFreeDayLimit[]
  lessonsOrderLimit: ILessonsOrderLimit
  maxLessonsLimit?: IMaxLessonsLimit
  desiredPeriodLimits: IDesiredPeriodLimit[]
}
