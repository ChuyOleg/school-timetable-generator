import { Component, Input } from '@angular/core';
import { IRoom } from "../../../models/room";
import { RoomService } from "../../../services/room/room.service";
import { RoomModalService } from "../../../services/room/room-modal.service";
import { DialogService } from "../../../services/dialog.service";

@Component({
  selector: 'app-room-block',
  templateUrl: './room-block.component.html'
})
export class RoomBlockComponent {

  @Input() room: IRoom

  constructor(
    private roomService: RoomService,
    private modalService: RoomModalService,
    private dialogService: DialogService
  ) {
  }

  delete() {
    this.dialogService.confirmDialog({
      title: "Ви впевнені?",
      message: `Хочете видалити [ ${this.room.name} ]?`,
      cancelText: "Ні",
      confirmText: "Так"
    }).subscribe(bool => {
      if (this.room.id && bool) {
        this.roomService.deleteById(this.room.id)
          .subscribe();
      }
    });
  }

  update() {
    this.roomService.roomToEdit = this.room;
    this.modalService.openUpdateModal();
  }

}
