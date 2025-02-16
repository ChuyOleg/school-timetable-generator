import { FormControl } from "@angular/forms";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";

export interface RoomConstraint {
  id?: number
  day: FormControl<EDayOfWeek | null>
  shift: FormControl<number | null>
  lessonFrom: FormControl<number | null>
  lessonTo: FormControl<number | null>
}
