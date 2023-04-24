import { Component, Input } from '@angular/core';
import { ITeacherInvalidLoading } from "../../../models/util";

@Component({
  selector: 'app-invalid-working-loading-block',
  templateUrl: './invalid-working-loading-block.component.html'
})
export class InvalidWorkingLoadingBlockComponent {

  @Input() teachersWithInvalidLoading: ITeacherInvalidLoading[]

}
