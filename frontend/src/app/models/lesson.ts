import { ITeacher } from "./teacher";
import { ISubject } from "./subject";
import { IRoom } from "./room";
import { ITimeSlot } from "./time-slot";

export interface ILesson {
  id?: number
  teacherDto: ITeacher
  subjectDto: ISubject
  roomDto: IRoom
  timeSlotDto: ITimeSlot
}
