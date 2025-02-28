import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { TeacherModalService } from "../../../services/teacher/teacher-modal.service";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { SubjectService } from "../../../services/subject/subject.service";

@Component({
  selector: 'app-edit-teacher',
  templateUrl: './edit-teacher.component.html'
})
export class EditTeacherComponent implements OnInit {

  constructor(
    public modalService: TeacherModalService,
    private teacherService: TeacherService,
    public subjectService: SubjectService
  ) {
  }

  teacherNameExist: boolean = false

  form = new FormGroup({
    name: new FormControl<string>(this.teacherService.teacherToEdit.name, [
      Validators.required
    ]),
    subjectDtoSet: new FormControl<ISubject[]>([], [
      Validators.required
    ]),
    maxHoursPerWeek: new FormControl<number>(this.teacherService.teacherToEdit.maxHoursPerWeek, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(40)
    ])
  })

  submit() {
    const teacherExist: boolean = this.teacherService.teachers
      .filter(teacher => teacher.id != this.teacherService.teacherToEdit.id)
      .find(teacher => teacher.name === this.form.value.name) != null;

    if (teacherExist) {
      this.teacherNameExist = true;
    } else {
      this.teacherService.edit({
        id: this.teacherService.teacherToEdit.id,
        name: this.form.value.name as string,
        subjectDtoSet: this.form.value.subjectDtoSet as ISubject[],
        maxHoursPerWeek: this.form.value.maxHoursPerWeek as number,
        limits: this.teacherService.teacherToEdit.limits
      }).subscribe(() => {
        this.teacherNameExist = false;
        this.modalService.closeUpdateModal();
      })
    }
  }

  ngOnInit(): void {
    this.subjectService.getAll().subscribe(() => {
      const selectedSubjects = this.teacherService.teacherToEdit.subjectDtoSet
        .map((selectedSubject) => {
          return this.subjectService.subjects.find((subject) => subject.id === selectedSubject.id);
        })
        .filter((subject) => subject != undefined) as ISubject[];
      this.form.controls['subjectDtoSet'].setValue(selectedSubjects);
    });
  }

  get name() {
    return this.form.controls.name as FormControl;
  }

  get maxHoursPerWeek() {
    return this.form.controls.maxHoursPerWeek as FormControl;
  }

}
