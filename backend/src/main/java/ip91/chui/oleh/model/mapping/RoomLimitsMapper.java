package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.room.RoomLimitDto;
import ip91.chui.oleh.model.entity.room.RoomLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomLimitsMapper {

  RoomLimitDto roomLimitToDto(RoomLimit roomLimit);

  RoomLimit dtoToRoomLimit(RoomLimitDto roomLimitDto);
}
