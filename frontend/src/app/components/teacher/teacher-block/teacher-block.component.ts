import { Component, Input } from '@angular/core';
import { ITeacher } from "../../../models/teacher";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { TeacherModalService } from "../../../services/teacher/teacher-modal.service";
import { DialogService } from "../../../services/dialog.service";

@Component({
  selector: 'app-teacher-block',
  templateUrl: './teacher-block.component.html'
})
export class TeacherBlockComponent {

  @Input() teacher: ITeacher;

  constructor(
    private teacherService: TeacherService,
    private modalService: TeacherModalService,
    private dialogService: DialogService
  ) {
  }

  delete() {
    this.dialogService.confirmDialog({
      title: "Are u sure?",
      message: `Do u want to delete [ ${this.teacher.name} ]?`,
      cancelText: "No",
      confirmText: "Yep"
    }).subscribe(bool => {
      if (this.teacher.id && bool) {
        this.teacherService.deleteById(this.teacher.id)
          .subscribe();
      }
    });
  }

  update() {
    this.teacherService.teacherToEdit = this.teacher;
    this.modalService.openUpdateModal();
  }

}
