import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ModalService } from "../../services/modal.service";
import { SubjectService } from "../../services/subject.service";

@Component({
  selector: 'app-create-subject',
  templateUrl: './create-subject.component.html'
})
export class CreateSubjectComponent {

  form = new FormGroup({
    title: new FormControl<string>('', [
      Validators.required
    ])
  })

  constructor(
    public modalService: ModalService,
    private subjectService: SubjectService
  ) {
  }


  submit() {
    this.subjectService.create({
      name: this.form.value.title as string
    }).subscribe(() => {
      this.modalService.close();
    })
  }

}
