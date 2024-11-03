import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GroupModalService } from "../../../services/group/group-modal.service";
import { GroupService } from "../../../services/group/group.service";
import { ITeacher } from "../../../models/teacher";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { IGroup } from "../../../models/group";

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html'
})
export class CreateGroupComponent implements OnInit {

  groupExist: boolean = false
  submitButtonIsPressed: boolean = false

  form: FormGroup<{
    gradeNumber: FormControl<number | null>;
    letter: FormControl<string | null>;
    shift: FormControl<number | null>;
    teacherDto: FormControl<ITeacher | null>; }>

  constructor(
    public modalService: GroupModalService,
    private groupService: GroupService,
    public teacherService: TeacherService
  ) {}

  submit() {
    this.submitButtonIsPressed = true
    if (this.form.valid) {
      const groupExist: boolean = this.checkGroupExistence();

      if (groupExist) {
        this.groupExist = true;
      } else {
        this.createGroup();
      }
    }
  }

  private checkGroupExistence(): boolean {
    return this.groupService.groups
      .find(group =>
        group.gradeNumber == this.form.value.gradeNumber &&
        group.letter === this.form.value.letter) != null
  }

  private createGroup() {
    this.groupService.create(this.extractGroupFromForm())
      .subscribe(() => this.groupIsCreatedPostActions())
  }

  private extractGroupFromForm(): IGroup {
    const { gradeNumber, letter, shift, teacherDto } = this.form.value;
    return {
      gradeNumber: gradeNumber as number,
      letter: letter as string,
      shift: shift as number,
      teacherDto: teacherDto as ITeacher };
  }

  private groupIsCreatedPostActions() {
    this.groupExist = false;
    this.submitButtonIsPressed = false;
    this.form.reset();
    this.modalService.closeCreateModal();
  }

  ngOnInit(): void {
    this.initForm();
    this.teacherService.getFreeClassTeachers().subscribe();
  }

  private initForm() {
    this.form = new FormGroup({
      gradeNumber: new FormControl<number|null>(null, [
        Validators.required,
        Validators.pattern("^[0-9]*$"),
        Validators.min(1),
        Validators.max(11)
      ]),
      letter: new FormControl<string>('', [
        Validators.required,
        Validators.pattern("^([A-ZА-ЯҐЄІЇ]){1}$")
      ]),
      shift: new FormControl<number|null>(null, [
        Validators.required,
        Validators.pattern("^[0-9]*$"),
        Validators.min(1),
        Validators.max(2)
      ]),
      teacherDto: new FormControl<ITeacher|null>(null, [
        Validators.required
      ])
    })
  }

  get gradeNumber() {
    return this.form.controls.gradeNumber as FormControl;
  }

  get letter() {
    return this.form.controls.letter as FormControl;
  }

  get shift() {
    return this.form.controls.shift as FormControl;
  }
}
