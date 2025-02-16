import { FormGroup } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { LessonLimitsGroup } from "./LessonLimitsGroup";

export interface LessonLimitsHolder {
  id?: number
  subject: ISubject
  controlGroup: FormGroup<LessonLimitsGroup>
}
