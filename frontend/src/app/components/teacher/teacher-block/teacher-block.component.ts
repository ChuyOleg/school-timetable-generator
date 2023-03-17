import { Component, Input } from '@angular/core';
import { ITeacher } from "../../../models/teacher";
import { TeacherService } from "../../../services/teacher/teacher.service";

@Component({
  selector: 'app-teacher-block',
  templateUrl: './teacher-block.component.html'
})
export class TeacherBlockComponent {

  @Input() teacher: ITeacher;

  constructor(
    private teacherService: TeacherService
  ) {
  }

}
