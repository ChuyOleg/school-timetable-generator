import { FormControl, Validators } from "@angular/forms";
import { IRoom } from "../../../models/room";
import { ITeacher } from "../../../models/teacher";
import { Utils } from "./Utils";
import { LessonLimitsHolder } from "../model/LessonLimitsHolder";

export class FormControlFactory {

  static createHoursFormControl(
    value: number | null,
    lessonLimits: LessonLimitsHolder[],
    maxHoursPerWeekForm: FormControl<number | null>): FormControl<number | null>  {

    const formControl: FormControl<number|null> = new FormControl<number|null>(value, [
      Validators.required,
      Validators.pattern("^[0-9]+(\\.5)?$"),
      Validators.min(0.5),
      Validators.max(10)]
    );

    formControl.valueChanges.subscribe(() =>
      Utils.calculateHoursSumAndSetIt(lessonLimits, maxHoursPerWeekForm));

    return formControl;
  }

  static createTeacherFormControl(value: ITeacher | null): FormControl<ITeacher | null> {
    return new FormControl<ITeacher | null>(value, [Validators.required]);
  }

  static createRoomFormControl(value: IRoom | null): FormControl<IRoom | null> {
    return new FormControl<IRoom | null>(value, []);
  }

  static createMaxHoursPerWeekFormControl(): FormControl<number | null> {
    return new FormControl<number | null>(0, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(40) ]);
  }
}
