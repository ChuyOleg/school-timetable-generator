import { Component, OnInit } from '@angular/core';
import { IRoom } from "../../models/room";
import { ActivatedRoute } from "@angular/router";
import { RoomService } from "../../services/room/room.service";

@Component({
  selector: 'app-room-details-page',
  templateUrl: './room-details-page.component.html'
})
export class RoomDetailsPageComponent implements OnInit {

  roomId: number;
  room: IRoom;

  constructor(
    private route: ActivatedRoute,
    private roomService: RoomService
  ) {}

  ngOnInit(): void {
    this.roomId = Number(this.route.snapshot.paramMap.get("id"));
    this.roomService.getById(this.roomId).subscribe(room => this.room = room);
  }
}
