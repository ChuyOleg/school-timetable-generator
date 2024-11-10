import { IGroupLimits } from "./group-limits";
import { ITeacher } from "./teacher/teacher";

export interface IGroup {
  id?: number
  gradeNumber: number
  letter: string
  shift: number
  teacherDto: ITeacher
  groupLimitsDto?: IGroupLimits
}
