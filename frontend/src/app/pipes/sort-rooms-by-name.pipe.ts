import { Pipe, PipeTransform } from '@angular/core';
import { IRoom } from "../models/room";

@Pipe({
  name: 'sortRoomsByName'
})
export class SortRoomsByNamePipe implements PipeTransform {

  transform(rooms: IRoom[]): IRoom[] {
    return rooms.sort((r1: IRoom, r2: IRoom) => {
      return r1.name.localeCompare(r2.name);
    })
  }

}
