import { FormControl } from "@angular/forms";

export interface MaxLessonsConstraint {
  id?: number
  count: FormControl<number | null>
  isActive: boolean
}
