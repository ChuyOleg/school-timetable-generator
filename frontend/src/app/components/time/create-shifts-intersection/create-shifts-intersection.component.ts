import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { ShiftsIntersectionModalService } from "../../../services/shift/shifts-intersection-modal.service";
import { ShiftsIntersectionService } from "../../../services/shift/shifts-intersection.service";
import { IShiftsIntersection } from "../../../models/shifts-intersection";

@Component({
  selector: 'app-create-shifts-intersection',
  templateUrl: './create-shifts-intersection.component.html'
})
export class CreateShiftsIntersectionComponent implements OnInit {

  isLoaded: boolean
  lessonNumbers: number[]

  formGroup: FormGroup<{
    shiftOneLessonNumberForm: FormControl<number | null>;
    shiftTwoLessonNumberForm: FormControl<number | null>; }>

  constructor(
    public modalService: ShiftsIntersectionModalService,
    private service: ShiftsIntersectionService
  ) {}

  submit() {
    if (!this.formGroup.valid) return;

    const shiftsIntersection = this.map();

    this.service.create(shiftsIntersection)
      .subscribe(() => this.modalService.closeCreateModal())
  }

  private map(): IShiftsIntersection {
    return {
      shiftOneLessonNumber: this.shiftOneLessonNumberForm.value as number,
      shiftTwoLessonNumber: this.shiftTwoLessonNumberForm.value as number };
  }

  ngOnInit(): void {
    this.lessonNumbers = [1, 2, 3, 4, 5, 6, 7, 8];

    this.formGroup = new FormGroup({
      shiftOneLessonNumberForm: new FormControl<number | null>(null,
        [Validators.required, this.uniqueLessonNumberValidator.bind(this, 1), this.lessonsOrderValidator.bind(this)]),
      shiftTwoLessonNumberForm: new FormControl<number | null>(null,
        [Validators.required, this.uniqueLessonNumberValidator.bind(this, 2), this.lessonsOrderValidator.bind(this)]) })

    this.isLoaded = true;
  }

  uniqueLessonNumberValidator(shift: number, control: FormControl<number | null>): ValidationErrors | null {
    const lessonNumber = control.value;
    if (lessonNumber == null) return null;

    if (this.hasShiftLessonNumber(shift, lessonNumber)) {
      return { isLessonNumberAlreadyUsed: true };
    }

    return null;
  }

  lessonsOrderValidator(): ValidationErrors | null {
    if (!this.formGroup) return null;

    const shiftOneLessonNumber = this.formGroup.controls.shiftOneLessonNumberForm.value as number;
    const shiftTwoLessonNumber = this.formGroup.controls.shiftTwoLessonNumberForm.value as number;

    if (!shiftOneLessonNumber || !shiftTwoLessonNumber || shiftTwoLessonNumber <= shiftOneLessonNumber) return null;

    return { invalidLessonOrder: true };
  }

  private hasShiftLessonNumber(shift: number, lessonNumber: number): boolean {
    switch (shift) {
      case 1: return this.service.shiftsIntersections.some(intersection => intersection.shiftOneLessonNumber === lessonNumber);
      case 2: return this.service.shiftsIntersections.some(intersection => intersection.shiftTwoLessonNumber === lessonNumber);
      default: throw new Error("Non-existent shift");
    }
  }

  get shiftOneLessonNumberForm() {
    return this.formGroup.controls.shiftOneLessonNumberForm;
  }

  get shiftTwoLessonNumberForm() {
    return this.formGroup.controls.shiftTwoLessonNumberForm;
  }
}
