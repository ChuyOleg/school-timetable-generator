import { ITeacher } from "./teacher";
import { ISubject } from "./subject";
import { IRoom } from "./room";
import { ITimeSlot } from "./time-slot";
import { IGroup } from "./group";

export interface ILesson {
  id?: number
  groupDto: IGroup
  teacherDto: ITeacher
  subjectDto: ISubject
  roomDto: IRoom
  timeSlotDto: ITimeSlot
}
