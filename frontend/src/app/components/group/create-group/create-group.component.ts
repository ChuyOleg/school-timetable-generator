import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GroupModalService } from "../../../services/group/group-modal.service";
import { GroupService } from "../../../services/group/group.service";

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html'
})
export class CreateGroupComponent {

  groupExist: boolean = false
  submitButtonIsPressed: boolean = false

  form = new FormGroup({
    gradeNumber: new FormControl<number>(0, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(11)
    ]),
    letter: new FormControl<string>('', [
      Validators.required,
      Validators.pattern("^([A-ZА-ЯҐЄІЇ]){1}$")
    ]),
    shift: new FormControl<number>(0, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(2)
    ])
  })

  constructor(
    public modalService: GroupModalService,
    private groupService: GroupService
  ) {
  }

  submit() {
    this.submitButtonIsPressed = true
    if (this.form.valid) {
      const groupExist: boolean = this.groupService.groups.find(group =>
        group.gradeNumber == this.form.value.gradeNumber && group.letter === this.form.value.letter
      ) != null

      if (groupExist) {
        this.groupExist = true;
      } else {
        this.groupService.create({
          gradeNumber: this.form.value.gradeNumber as number,
          letter: this.form.value.letter as string,
          shift: this.form.value.shift as number
        }).subscribe(() => {
          this.groupExist = false;
          this.submitButtonIsPressed = false;
          this.modalService.closeCreateModal();
        })
      }
    }
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
