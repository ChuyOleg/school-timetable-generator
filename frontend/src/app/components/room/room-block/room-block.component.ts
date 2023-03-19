import { Component, Input } from '@angular/core';
import { IRoom } from "../../../models/room";
import { RoomService } from "../../../services/room/room.service";

@Component({
  selector: 'app-room-block',
  templateUrl: './room-block.component.html'
})
export class RoomBlockComponent {

  @Input() room: IRoom

  constructor(
    private roomService: RoomService
  ) {
  }

}
