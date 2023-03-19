import { Component, Input } from '@angular/core';
import { RoomModalService } from "../../../services/room/room-modal.service";

@Component({
  selector: 'app-room-modal',
  templateUrl: './room-modal.component.html'
})
export class RoomModalComponent {

  @Input() title: string

  constructor(public modalService: RoomModalService) {
  }

}
