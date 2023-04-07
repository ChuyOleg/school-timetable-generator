import { ISubject } from "./subject";
import { ITeacher } from "./teacher";

export interface ISubjectTeacherInGroup {
  id?: number
  subjectDto: ISubject
  teacherDto: ITeacher
}
