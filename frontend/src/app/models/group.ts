import { IGroupLimits } from "./group-limits";
import { ILesson } from "./lesson";

export interface IGroup {
  id?: number
  gradeNumber: number
  letter: string
  shift: number
  lessonDtoSet: ILesson[]
  groupLimitsDto: IGroupLimits
}
