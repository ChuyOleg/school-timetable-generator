import { IRoomLimit } from "./room-limit";

export interface IRoom {
  id?: number
  name: string
  capacity: number
  roomLimitDtoSet?: IRoomLimit[]
}
