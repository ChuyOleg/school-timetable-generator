import { FormControl } from "@angular/forms";
import { ITeacher } from "../../../models/teacher";
import { IRoom } from "../../../models/room";

export interface LessonLimitsGroup {
  hours: FormControl<number | null>
  teacher: FormControl<ITeacher | null>
  room: FormControl<IRoom | null>
  teacher2?: FormControl<ITeacher | null>
  room2?: FormControl<IRoom | null>
}
