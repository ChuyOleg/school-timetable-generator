import { RoomConstraint } from "../model/RoomConstraint";
import { IRoomLimit } from "../../../models/room-limit";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";

export class RoomLimitMapper {

  static mapMany(roomConstraints: RoomConstraint[]): IRoomLimit[] {
    return roomConstraints.map(roomConstraint => RoomLimitMapper.map(roomConstraint));
  }

  static map(roomConstraint: RoomConstraint): IRoomLimit {
    return {
      id: roomConstraint.id,
      day: roomConstraint.day.value as EDayOfWeek,
      shift: roomConstraint.shift.value as number,
      lessonNumberFrom: roomConstraint.lessonFrom.value as number,
      lessonNumberTo: roomConstraint.lessonTo.value as  number
    }
  }
}
