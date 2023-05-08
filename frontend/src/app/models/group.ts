import { IGroupLimits } from "./group-limits";
import { ITeacher } from "./teacher";

export interface IGroup {
  id?: number
  gradeNumber: number
  letter: string
  shift: number
  teacherDto: ITeacher
  groupLimitsDto?: IGroupLimits
}
