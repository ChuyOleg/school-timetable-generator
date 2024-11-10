import { Component, Input, OnInit } from '@angular/core';
import { PeriodConstraint } from "../../model/constraint/PeriodConstraint";
import { CommonUtils } from "../../../../utils/CommonUtils";
import { EDayOfWeek } from "../../../../models/enumeration/day-of-week";
import { FormControlFactory } from "../../utils/FormControlFactory";

@Component({
  selector: 'app-desired-period-constraints',
  templateUrl: './desired-period-constraints.component.html'
})
export class DesiredPeriodConstraintsComponent implements OnInit {

  @Input() constraints: PeriodConstraint[];

  days: EDayOfWeek[];
  lessonNumbers: number[];

  add(): void {
    this.constraints.push(this.buildConstraint());
  }

  async delete(deletedConstraint: PeriodConstraint) {
    await CommonUtils.artificialPause(500);

    const index = this.constraints.indexOf(deletedConstraint);
    this.constraints.splice(index, 1);
  }

  private buildConstraint(): PeriodConstraint {
    return {
      day: FormControlFactory.dayFormControl(null),
      lessonFrom: FormControlFactory.lessonNumberFormControl(null),
      lessonTo: FormControlFactory.lessonNumberFormControl(null)
    }
  }

  ngOnInit(): void {
    this.lessonNumbers = [1, 2, 3, 4, 5, 6, 7, 8];
    this.days = Object.values(EDayOfWeek);
  }
}
