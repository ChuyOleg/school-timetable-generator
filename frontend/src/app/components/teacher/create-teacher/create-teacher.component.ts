import { Component, OnInit } from '@angular/core';
import { TeacherModalService } from "../../../services/teacher/teacher-modal.service";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { SubjectService } from "../../../services/subject/subject.service";
import { ITeacher } from "../../../models/teacher";

@Component({
  selector: 'app-create-teacher',
  templateUrl: './create-teacher.component.html'
})
export class CreateTeacherComponent implements OnInit {

  teacherNameExist: boolean = false
  submitButtonIsPressed: boolean = false

  form: FormGroup<{
    name: FormControl<string | null>;
    subjectDtoSet: FormControl<ISubject[] | null>;
    maxHoursPerWeek: FormControl<number | null>; }>;

  constructor(
    public modalService: TeacherModalService,
    private teacherService: TeacherService,
    public subjectService: SubjectService
  ) {}

  submit() {
    this.submitButtonIsPressed = true;
    if (this.form.valid) {
      const teacherExist = this.checkTeacherExistence();

      if (teacherExist) {
        this.teacherNameExist = true;
      } else {
        this.createTeacher()
      }
    }
  }

  private checkTeacherExistence(): boolean {
    return this.teacherService.teachers
      .find(teacher => teacher.name === this.form.value.name) != null;
  }

  private createTeacher() {
    this.teacherService.create(this.extractTeacherFromForm())
      .subscribe(() => this.teacherIsCreatedPostActions())
  }

  private extractTeacherFromForm(): ITeacher {
    const { name, subjectDtoSet, maxHoursPerWeek } = this.form.value;
    return {
      name: name as string,
      subjectDtoSet: subjectDtoSet as ISubject[],
      maxHoursPerWeek: maxHoursPerWeek as number };
  }

  private teacherIsCreatedPostActions() {
    this.teacherNameExist = false;
    this.submitButtonIsPressed = false;
    this.form.reset();
    this.modalService.closeCreateModal();
  }

  get name(): FormControl<string> {
    return this.form.controls.name as FormControl;
  }

  get maxHoursPerWeek(): FormControl<number> {
    return this.form.controls.maxHoursPerWeek as FormControl;
  }

  ngOnInit(): void {
    this.initForm();
    this.subjectService.getAll().subscribe();
  }

  private initForm() {
    this.form = new FormGroup({
      name: new FormControl<string>('', [Validators.required]),
      subjectDtoSet: new FormControl<ISubject[]>([], [Validators.required]),
      maxHoursPerWeek: new FormControl<number | null>(null, [
        Validators.required,
        Validators.pattern("^[0-9]*$"),
        Validators.min(1),
        Validators.max(40)
      ])
    })
  }
}
