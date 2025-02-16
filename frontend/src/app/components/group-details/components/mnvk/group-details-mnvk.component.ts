import { Component, Input } from '@angular/core';
import { EDayOfWeek } from "../../../../models/enumeration/day-of-week";
import { GroupLimitsPageState } from "../../model/GroupLimitsPageState";
import { InterschoolCombine } from "../../model/InterschoolCombine";

@Component({
  selector: 'app-group-details-mnvk',
  templateUrl: './group-details-mnvk.component.html'
})
export class GroupDetailsMnvkComponent {

  @Input() pageState: GroupLimitsPageState
  @Input() interschoolCombine: InterschoolCombine

  days: EDayOfWeek[] = Object.values(EDayOfWeek)
  lessonNumbers: number[] = [1, 2, 3, 4, 5, 6, 7, 8]

  get isPicked(): boolean {
    return this.pageState.isInterschoolCombinePicked
  }

  handleToggle() {
    this.pageState.isInterschoolCombinePicked = !this.pageState.isInterschoolCombinePicked;
  }
}
