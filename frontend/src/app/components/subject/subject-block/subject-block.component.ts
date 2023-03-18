import { Component, Input } from "@angular/core";
import { ISubject } from "../../../models/subject";
import { SubjectService } from "../../../services/subject/subject.service";
import { SubjectModalService } from "../../../services/subject/subject-modal.service";
import { DialogService } from "../../../services/dialog.service";

@Component({
  selector: 'app-subject-block',
  templateUrl: './subject-block.component.html'
})
export class SubjectBlockComponent {
  @Input() subject: ISubject

  constructor(
    private subjectService: SubjectService,
    private modalService: SubjectModalService,
    private dialogService: DialogService
  ) {
  }

  delete() {
    this.dialogService.confirmDialog({
      title: "Are u sure?",
      message: `Do u want to delete [ ${this.subject.name} ]?`,
      cancelText: "No",
      confirmText: "Yep"
    }).subscribe(bool => {
      if (this.subject.id && bool) {
        this.subjectService.deleteById(this.subject.id)
          .subscribe();
      }
    });
  }

  update() {
    this.subjectService.subjectToEdit = this.subject;
    this.modalService.openUpdateModal();
  }

}
