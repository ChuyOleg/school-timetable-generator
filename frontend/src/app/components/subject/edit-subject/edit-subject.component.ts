import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { SubjectModalService } from "../../../services/subject/subject-modal.service";
import { SubjectService } from "../../../services/subject/subject.service";

@Component({
  selector: 'app-edit-subject',
  templateUrl: './edit-subject.component.html'
})
export class EditSubjectComponent {

  constructor(
    public modalService: SubjectModalService,
    private subjectService: SubjectService
  ) {
  }

  subjectNameExist: boolean = false

  form = new FormGroup({
    name: new FormControl<string>(this.subjectService.subjectToEdit.name, [
      Validators.required
    ])
  })

  submit() {
    const subjectExist: boolean = this.subjectService.subjects
      .filter(subject => subject.id != this.subjectService.subjectToEdit.id)
      .find(subject => subject.name === this.form.value.name) != null;

    if (subjectExist) {
      this.subjectNameExist = true;
    } else {
      this.subjectService.edit({
        id: this.subjectService.subjectToEdit.id,
        name: this.form.value.name as string
      }).subscribe(() => {
        this.subjectNameExist = false;
        this.modalService.closeUpdateModal();
      })
    }
  }

  get name() {
    return this.form.controls.name as FormControl
  }

}
