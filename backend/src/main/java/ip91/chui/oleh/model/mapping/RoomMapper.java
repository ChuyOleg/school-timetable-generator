package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.RoomDto;
import ip91.chui.oleh.model.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

  RoomDto roomToDto(Room room);

  Room dtoToRoom(RoomDto roomDto);

}
