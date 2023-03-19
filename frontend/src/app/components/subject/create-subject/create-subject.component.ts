import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { SubjectModalService } from "../../../services/subject/subject-modal.service";
import { SubjectService } from "../../../services/subject/subject.service";

@Component({
  selector: 'app-create-subject',
  templateUrl: './create-subject.component.html'
})
export class CreateSubjectComponent {

  subjectNameExist: boolean = false
  submitButtonIsPressed: boolean = false

  form = new FormGroup({
    name: new FormControl<string>('', [
      Validators.required
    ])
  })

  constructor(
    public modalService: SubjectModalService,
    private subjectService: SubjectService
  ) {
  }

  submit() {
    this.submitButtonIsPressed = true
    if (this.form.valid) {
      const subjectExist: boolean = this.subjectService.subjects
        .find(subject => subject.name === this.form.value.name) != null;

      if (subjectExist) {
        this.subjectNameExist = true;
      } else {
        this.subjectService.create({
          name: this.form.value.name as string
        }).subscribe(() => {
          this.subjectNameExist = false;
          this.submitButtonIsPressed = false;
          this.modalService.closeCreateModal();
        })
      }
    }
  }

  get name() {
    return this.form.controls.name as FormControl;
  }

}
