import { Component, Input } from '@angular/core';
import { ITeacherInvalidLoading } from "../../../models/util/teacher-invalid-loading";

@Component({
  selector: 'app-invalid-working-loading-block',
  templateUrl: './invalid-working-loading-block.component.html'
})
export class InvalidWorkingLoadingBlockComponent {

  @Input() teachersWithInvalidLoading: ITeacherInvalidLoading[]

}
