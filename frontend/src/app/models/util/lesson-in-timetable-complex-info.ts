import { ILessonLightweight } from "./lesson-lightweight";

export interface ILessonInTimetableComplexInfo {
  lesson1?: ILessonLightweight
  lesson2?: ILessonLightweight
  oddLesson?: ILessonLightweight
  evenLesson?: ILessonLightweight
}
