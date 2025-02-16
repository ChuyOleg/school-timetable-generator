import { Component, Input, OnInit } from '@angular/core';
import { CommonUtils } from "../../../../utils/CommonUtils";
import { MaxLessonsConstraint } from "../../model/constraint/MaxLessonsConstraint";

@Component({
  selector: 'app-max-lessons-constraint',
  templateUrl: './max-lessons-constraint.component.html'
})
export class MaxLessonsConstraintComponent implements OnInit {

  @Input() constraint: MaxLessonsConstraint;

  isMaxLessonsConstraintRemoved: boolean;
  lessonCounts: number[];

  addMaxLessonsPerDayConstraint(): void {
    this.constraint.isActive = true;
  }

  async deleteMaxLessonsConstraint() {
    this.isMaxLessonsConstraintRemoved = true;
    await CommonUtils.artificialPause(500);
    this.isMaxLessonsConstraintRemoved = false;

    this.constraint.count.setValue(null);
    this.constraint.isActive = false;
  }

  ngOnInit(): void {
    this.isMaxLessonsConstraintRemoved = false;
    this.lessonCounts = [1, 2, 3, 4, 5, 6, 7, 8];
  }
}
