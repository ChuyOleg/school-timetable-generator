import { FormControl } from "@angular/forms";
import { EImportanceLevel } from "../../../../models/enumeration/importance-level";

export interface LessonsOrderConstraint {
  id?: number
  importanceLevel: FormControl<EImportanceLevel>
}
