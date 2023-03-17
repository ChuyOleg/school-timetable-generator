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

  form = new FormGroup({
    title: new FormControl<string>('', [
      Validators.required
    ])
  })

  constructor(
    public modalService: SubjectModalService,
    private subjectService: SubjectService
  ) {
  }

  submit() {
    if (this.form.valid) {
      const subjectExist: boolean = this.subjectService.subjects
        .find(subject => subject.name === this.form.value.title) != null;

      if (subjectExist) {
        this.subjectNameExist = true;
      } else {
        this.subjectService.create({
          name: this.form.value.title as string
        }).subscribe(() => {
          this.subjectNameExist = false;
          this.modalService.closeCreateModal();
        })
      }

    }
  }

  get title() {
    return this.form.controls.title as FormControl;
  }

}
