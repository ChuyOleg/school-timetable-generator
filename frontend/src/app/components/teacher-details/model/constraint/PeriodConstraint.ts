import { FormControl } from "@angular/forms";
import { EDayOfWeek } from "../../../../models/enumeration/day-of-week";

export interface PeriodConstraint {
  id?: number
  day: FormControl<EDayOfWeek | null>
  lessonFrom: FormControl<number | null>
  lessonTo: FormControl<number | null>
}
