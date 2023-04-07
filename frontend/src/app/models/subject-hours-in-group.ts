import { ISubject } from "./subject";

export interface ISubjectHoursInGroup {
  id?: number
  subjectDto: ISubject
  hours: number
}
