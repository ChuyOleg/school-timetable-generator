import { Component, OnInit } from '@angular/core';
import { TeacherModalService } from "../../../services/teacher/teacher-modal.service";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { SubjectService } from "../../../services/subject/subject.service";

@Component({
  selector: 'app-create-teacher',
  templateUrl: './create-teacher.component.html'
})
export class CreateTeacherComponent implements OnInit {

  teacherNameExist: boolean = false
  submitButtonIsPressed: boolean = false

  form = new FormGroup({
    name: new FormControl<string>('', [
      Validators.required
    ]),
    subjectDtoSet: new FormControl<ISubject[]>([], [
      Validators.required
    ]),
    maxHoursPerWeek: new FormControl<number>(0, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(40)
    ])
  })

  constructor(
    public modalService: TeacherModalService,
    private teacherService: TeacherService,
    public subjectService: SubjectService
  ) {
  }

  submit() {
    this.submitButtonIsPressed = true;
    if (this.form.valid) {
      const teacherExist: boolean = this.teacherService.teachers
        .find(teacher => teacher.name === this.form.value.name) != null;

      if (teacherExist) {
        this.teacherNameExist = true;
      } else {
        this.teacherService.create({
          name: this.form.value.name as string,
          subjectDtoSet: this.form.value.subjectDtoSet as ISubject[],
          maxHoursPerWeek: this.form.value.maxHoursPerWeek as number
        }).subscribe(() => {
          this.teacherNameExist = false;
          this.submitButtonIsPressed = false;
          this.modalService.closeCreateModal();
        })
      }
    }
  }

  get name() {
    return this.form.controls.name as FormControl;
  }

  get maxHoursPerWeek() {
    return this.form.controls.maxHoursPerWeek as FormControl;
  }

  ngOnInit(): void {
    this.subjectService.getAll().subscribe();
  }

}
