import { IGroupLimits } from "./group-limits";
import { ILesson } from "./lesson";
import { ITeacher } from "./teacher";

export interface IGroup {
  id?: number
  gradeNumber: number
  letter: string
  shift: number
  teacherDto: ITeacher
  lessonDtoSet?: ILesson[]
  groupLimitsDto?: IGroupLimits
}
