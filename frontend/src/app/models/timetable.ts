import { ILessonLightweight } from "./util/lesson-lightweight";

export interface ITimetable {
  id?: number,
  lessons: ILessonLightweight[]
}
