import { FormControl } from "@angular/forms";
import { EDayOfWeek } from "../../../../models/enumeration/day-of-week";

export interface FreeDayConstraint {
  id?: number
  day: FormControl<EDayOfWeek | null>
}
