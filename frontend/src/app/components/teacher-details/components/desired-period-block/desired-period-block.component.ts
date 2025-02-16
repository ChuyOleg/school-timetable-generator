import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PeriodConstraint } from "../../model/constraint/PeriodConstraint";
import { EDayOfWeek } from "../../../../models/enumeration/day-of-week";

@Component({
  selector: 'app-desired-period-block',
  templateUrl: './desired-period-block.component.html'
})
export class DesiredPeriodBlockComponent {

  @Input() constraint: PeriodConstraint;
  @Input() days: EDayOfWeek[];
  @Input() lessonNumbers: number[];
  @Input() shifts: number[]
  @Output() deleteEvent = new EventEmitter<void>;

  isDeleted: boolean;

  delete() {
    this.isDeleted = true;
    this.deleteEvent.emit();
  }

  areLessonNumbersValid(constraint: PeriodConstraint): boolean {
    const lessonFrom = constraint.lessonFrom.value;
    const lessonTo = constraint.lessonTo.value;

    if (lessonFrom == null || lessonTo  == null) return true;

    return lessonTo > lessonFrom;
  }
}
