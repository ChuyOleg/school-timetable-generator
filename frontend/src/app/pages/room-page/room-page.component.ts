import { Component, OnInit } from '@angular/core';
import { RoomService } from "../../services/room/room.service";
import { RoomModalService } from "../../services/room/room-modal.service";
import { delay } from "rxjs";

@Component({
  selector: 'app-room-page',
  templateUrl: './room-page.component.html'
})
export class RoomPageComponent implements OnInit {

  loading: boolean = false
  term = ''
  maxRoomNumber = 10;

  constructor(
    public roomService: RoomService,
    public modalService: RoomModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.roomService.getAll().pipe(delay(500)).subscribe(() => {
      this.loading = false;
    })
  }

}
