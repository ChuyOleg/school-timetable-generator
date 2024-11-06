import { Pipe, PipeTransform } from '@angular/core';
import { RoomConstraint } from "../components/room-details/model/RoomConstraint";
import { EDayOfWeek } from "../models/enumeration/day-of-week";

@Pipe({
  name: 'sortRoomLimitsByDay'
})
export class SortRoomLimitsByDayPipe implements PipeTransform {

  transform(roomLimits: RoomConstraint[]): RoomConstraint[] {
    return roomLimits.sort((limit1, limit2) =>
      this.order(limit1.day.value) - this.order(limit2.day.value));
  }

  private order(day: EDayOfWeek | null): number {
    switch (day) {
      case EDayOfWeek.MONDAY:
        return 1;
      case EDayOfWeek.TUESDAY:
        return 2;
      case EDayOfWeek.WEDNESDAY:
        return 3;
      case EDayOfWeek.THURSDAY:
        return 4;
      case EDayOfWeek.FRIDAY:
        return 5;
      default:
        return 6;
    }
  }
}
