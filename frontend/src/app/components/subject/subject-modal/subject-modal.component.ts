import { Component, Input } from '@angular/core';
import { SubjectModalService } from "../../../services/subject/subject-modal.service";

@Component({
  selector: 'app-subject-modal',
  templateUrl: './subject-modal.component.html'
})
export class SubjectModalComponent {

  @Input() title: string

  constructor(public modalService: SubjectModalService) {
  }

}
