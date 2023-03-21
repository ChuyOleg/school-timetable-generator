import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { RoomModalService } from "../../../services/room/room-modal.service";
import { RoomService } from "../../../services/room/room.service";

@Component({
  selector: 'app-edit-room',
  templateUrl: './edit-room.component.html'
})
export class EditRoomComponent {

  constructor(
    public modalService: RoomModalService,
    private roomService: RoomService
  ) {
  }

  roomNameExist: boolean = false

  form = new FormGroup({
    name: new FormControl<string>(this.roomService.roomToEdit.name, [
      Validators.required
    ]),
    capacity: new FormControl<number>(this.roomService.roomToEdit.capacity, [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
      Validators.max(999)
    ])
  })

  submit() {
    const roomExist: boolean = this.roomService.rooms
      .filter(room => room.id != this.roomService.roomToEdit.id)
      .find(room => room.name === this.form.value.name) != null;

    if (roomExist) {
      this.roomNameExist = true;
    } else {
      this.roomService.edit({
        id: this.roomService.roomToEdit.id,
        name: this.form.value.name as string,
        capacity: this.form.value.capacity as number
      }).subscribe(() => {
        this.roomNameExist = false;
        this.modalService.closeCreateModal();
      })
    }
  }

  get name() {
    return this.form.controls.name as FormControl;
  }

  get capacity() {
    return this.form.controls.capacity as FormControl;
  }

}
