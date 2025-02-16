import { Component, Input } from '@angular/core';
import { FreeDayConstraint } from "../../model/constraint/FreeDayConstraint";
import { CommonUtils } from "../../../../utils/CommonUtils";
import { FormControlFactory } from "../../utils/FormControlFactory";

@Component({
  selector: 'app-free-day-constraints',
  templateUrl: './free-day-constraints.component.html'
})
export class FreeDayConstraintsComponent {

  @Input() constraints: FreeDayConstraint[];

  add(): void {
    this.constraints.push(this.buildConstraint());
  }

  async delete(deletedConstraint: FreeDayConstraint) {
    await CommonUtils.artificialPause(500);

    const index = this.constraints.indexOf(deletedConstraint);
    this.constraints.splice(index, 1);
  }

  private buildConstraint(): FreeDayConstraint {
    return { day: FormControlFactory.dayFormControl(null) };
  }
}
