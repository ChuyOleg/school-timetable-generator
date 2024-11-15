import { FormControl, Validators } from "@angular/forms";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";

export class FormControlFactory {

  static dayFormControl(day: EDayOfWeek | null): FormControl<EDayOfWeek | null> {
    return new FormControl<EDayOfWeek | null>(day, Validators.required);
  }

  static shiftFormControl(shift: number | null): FormControl<number | null> {
    return new FormControl<number | null>(shift, Validators.required);
  }

  static lessonNumberFormControl(value: number | null): FormControl<number | null> {
    return new FormControl<number | null>(value, Validators.required);
  }
}
