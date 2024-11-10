import { ISubject } from "../subject";
import { ITeacherLimits } from "./teacher-limits";

export interface ITeacher {
  id?: number
  name: string
  subjectDtoSet: ISubject[]
  maxHoursPerWeek: number
  limits: ITeacherLimits
}
