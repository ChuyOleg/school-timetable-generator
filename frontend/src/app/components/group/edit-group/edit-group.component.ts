import { Component, OnInit } from '@angular/core';
import { GroupModalService } from "../../../services/group/group-modal.service";
import { GroupService } from "../../../services/group/group.service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ITeacher } from "../../../models/teacher";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { tap } from "rxjs";

@Component({
  selector: 'app-edit-group',
  templateUrl: './edit-group.component.html'
})
export class EditGroupComponent implements OnInit {

  groupExist: boolean = false

  constructor(
    public modalService: GroupModalService,
    private groupService: GroupService,
    public teacherService: TeacherService
  ) {
  }

  form = new FormGroup({
    gradeNumber: new FormControl<number>(this.groupService.groupToEdit.gradeNumber, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(11)
    ]),
    letter: new FormControl<string>(this.groupService.groupToEdit.letter, [
      Validators.required,
      Validators.pattern("^([A-ZА-ЯҐЄІЇ]){1}$")
    ]),
    shift: new FormControl<number>(this.groupService.groupToEdit.shift, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(2)
    ]),
    teacherDto: new FormControl<ITeacher|null>(this.groupService.groupToEdit.teacherDto, [
      Validators.required
    ])
  })

  submit() {
    const groupExist: boolean = this.groupService.groups
      .filter(group => group.id != this.groupService.groupToEdit.id)
      .find(group =>
        group.gradeNumber == this.form.value.gradeNumber && group.letter === this.form.value.letter
      ) != null

    if (groupExist) {
      this.groupExist = true;
    } else {
      this.groupService.edit({
        id: this.groupService.groupToEdit.id,
        gradeNumber: this.form.value.gradeNumber as number,
        letter: this.form.value.letter as string,
        shift: this.form.value.shift as number,
        teacherDto: this.form.value.teacherDto as ITeacher,
        groupLimitsDto: this.groupService.groupToEdit.groupLimitsDto
      }).subscribe(() => {
        this.groupExist = false;
        this.modalService.closeUpdateModal();
      })
    }
  }

  ngOnInit(): void {
    const id = this.groupService.groupToEdit.id;
    if (id) {
      this.groupService.getById(id).subscribe(group => {
        this.groupService.groupToEdit = group;
      })
    }

    this.teacherService.getFreeClassTeachers()
      .pipe(
        tap(() => {
          if (this.form.value.teacherDto) {
            this.teacherService.freeClassTeachers.push(this.form.value.teacherDto)
          }
        }))
      .subscribe();
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
