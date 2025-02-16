package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.entity.room.Room;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoomMapper {

  @Mappings({
      @Mapping(target = "roomLimitDtoSet", source = "roomLimitSet")
  })
  RoomDto roomToDto(Room room);

  @Mappings({
      @Mapping(target = "roomLimitSet", source = "roomLimitDtoSet")
  })
  Room dtoToRoom(RoomDto roomDto);

  @AfterMapping
  default void setRoomForRoomLimitsSet(@MappingTarget Room room) {
    if (room.getRoomLimitSet() != null) {
      room.getRoomLimitSet().forEach(limit -> limit.setRoom(room));
    }
  }
}
