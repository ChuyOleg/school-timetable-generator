import { ILesson } from "../lesson";

export interface ITimetableFines {
  subjectFines: ILesson[]
  teacherFines: ILesson[]
  roomFines: ILesson[]
}
