import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { RoomModalService } from "../../../services/room/room-modal.service";
import { RoomService } from "../../../services/room/room.service";

@Component({
  selector: 'app-create-room',
  templateUrl: './create-room.component.html'
})
export class CreateRoomComponent {

  roomNameExist: boolean = false
  submitButtonIsPressed: boolean = false

  form = new FormGroup({
    name: new FormControl<string>('', [
      Validators.required
    ]),
    capacity: new FormControl<number>(0, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(999)
    ])
  })

  constructor(
    public modalService: RoomModalService,
    private roomService: RoomService
  ) {
  }

  submit() {
    this.submitButtonIsPressed = true
    if (this.form.valid) {
      const roomExist: boolean = this.roomService.rooms
        .find(room => room.name === this.form.value.name) != null;

      if (roomExist) {
        this.roomNameExist = true;
      } else {
        this.roomService.create({
          name: this.form.value.name as string,
          capacity: this.form.value.capacity as number
        }).subscribe(() => {
          this.roomNameExist = false;
          this.submitButtonIsPressed = false;
          this.modalService.closeCreateModal();
        })
      }
    }
  }

  get name() {
    return this.form.controls.name as FormControl;
  }

  get capacity() {
    return this.form.controls.capacity as FormControl;
  }

}
