import { FormControl } from "@angular/forms";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";

export interface InterschoolCombine {
  day: FormControl<EDayOfWeek | null>,
  lessonNumber: FormControl<number | null>
}
