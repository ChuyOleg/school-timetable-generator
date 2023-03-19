import { Component, OnInit } from '@angular/core';
import { RoomService } from "../../../services/room/room.service";

@Component({
  selector: 'app-room-page',
  templateUrl: './room-page.component.html'
})
export class RoomPageComponent implements OnInit {

  loading: boolean = false
  term = ''

  constructor(
    public roomService: RoomService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.roomService.getAll().subscribe(() => {
      this.loading = false;
    })
  }

}
