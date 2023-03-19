import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { SubjectModalService } from "../../../services/subject/subject-modal.service";
import { SubjectService } from "../../../services/subject/subject.service";

@Component({
  selector: 'app-edit-subject',
  templateUrl: './edit-subject.component.html'
})
export class EditSubjectComponent {

  subjectNameExist: boolean = false

  form = new FormGroup({
    title: new FormControl<string>(this.subjectService.subjectToEdit.name, [
      Validators.required
    ])
  })

  constructor(
    public modalService: SubjectModalService,
    private subjectService: SubjectService
  ) {
  }


  submit() {
    const subjectExist: boolean = this.subjectService.subjects
      .filter(subject => subject.id != this.subjectService.subjectToEdit.id)
      .find(subject => subject.name === this.form.value.title) != null;

    if (subjectExist) {
      this.subjectNameExist = true;
    } else {
      this.subjectService.edit({
        id: this.subjectService.subjectToEdit.id,
        name: this.form.value.title as string
      }).subscribe(() => {
        this.subjectNameExist = false;
        this.modalService.closeUpdateModal();
      })
    }
  }

  get title() {
    return this.form.controls.title as FormControl
  }

}