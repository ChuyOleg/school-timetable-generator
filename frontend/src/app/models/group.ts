import { EGradeNumber } from "./enumeration/grade-number";
import { EShift } from "./enumeration/shift";
import { IGroupLimits } from "./group-limits";
import { ILesson } from "./lesson";

export interface IGroup {
  id?: number
  gradeNumber: EGradeNumber
  letter: string
  shift: EShift
  lessonDtoSet: ILesson[]
  groupLimitsDto: IGroupLimits
}
