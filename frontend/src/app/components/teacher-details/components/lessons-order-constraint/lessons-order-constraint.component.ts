import { Component, Input, OnInit } from '@angular/core';
import { LessonsOrderConstraint } from "../../model/constraint/LessonsOrderConstraint";
import { EImportanceLevel } from "../../../../models/enumeration/importance-level";

@Component({
  selector: 'app-lessons-order-constraint',
  templateUrl: './lessons-order-constraint.component.html'
})
export class LessonsOrderConstraintComponent implements OnInit {

  @Input() constraint: LessonsOrderConstraint;

  importanceLevels: EImportanceLevel[];

  ngOnInit(): void {
    this.importanceLevels = Object.values(EImportanceLevel);
  }
}
