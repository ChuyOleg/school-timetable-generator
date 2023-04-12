import { ISubject } from "./subject";
import { ITeacher } from "./teacher";
import { IRoom } from "./room";

export interface ISubjectTeacherRoomInGroup {
  id?: number
  subjectDto: ISubject
  teacherDto: ITeacher
  roomDto?: IRoom
}
