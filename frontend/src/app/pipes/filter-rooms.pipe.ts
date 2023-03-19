import { Pipe, PipeTransform } from '@angular/core';
import { IRoom } from "../models/room";

@Pipe({
  name: 'filterRooms'
})
export class FilterRoomsPipe implements PipeTransform {

  transform(rooms: IRoom[], search: string): IRoom[] {
    if (search.length === 0) return rooms;
    return rooms.filter(r => r.roomName.toLowerCase().includes(search.toLowerCase()));
  }

}
