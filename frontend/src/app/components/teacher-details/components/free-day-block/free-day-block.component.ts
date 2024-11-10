import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FreeDayConstraint } from "../../model/constraint/FreeDayConstraint";
import { EDayOfWeek } from "../../../../models/enumeration/day-of-week";

@Component({
  selector: 'app-free-day-block',
  templateUrl: './free-day-block.component.html'
})
export class FreeDayBlockComponent implements OnInit {

  @Input() constraint: FreeDayConstraint;
  @Output() deleteEvent = new EventEmitter<void>();

  isDeleted: boolean;
  days: EDayOfWeek[];

  delete() {
    this.isDeleted = true;
    this.deleteEvent.emit();
  }

  ngOnInit(): void {
    this.days = Object.values(EDayOfWeek);
  }
}
