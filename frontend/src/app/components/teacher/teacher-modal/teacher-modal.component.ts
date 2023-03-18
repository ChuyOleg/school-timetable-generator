import { Component, Input } from '@angular/core';
import { TeacherModalService } from "../../../services/teacher/teacher-modal.service";

@Component({
  selector: 'app-teacher-modal',
  templateUrl: './teacher-modal.component.html'
})
export class TeacherModalComponent {

  @Input() title:  string

  constructor(public modalService: TeacherModalService) {
  }

}
