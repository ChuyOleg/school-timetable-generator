import { ISubject } from "./subject";
import { ITeacher } from "./teacher/teacher";
import { IRoom } from "./room";

export interface ISubjectLimits {
  id?: number
  subjectDto: ISubject
  hours: number
  teacherDto: ITeacher
  roomDto?: IRoom
  teacherDto2?: ITeacher
  roomDto2?: IRoom
}
