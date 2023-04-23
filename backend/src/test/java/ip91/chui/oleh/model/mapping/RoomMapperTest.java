package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.RoomDto;
import ip91.chui.oleh.model.entity.Room;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class RoomMapperTest {

  private static final String ROOM_NUMBER = "Room №1";
  private static final int CAPACITY = 30;

  private final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

  @Test
  public void Should_ConvertRoomToDto_When_RoomIsValid() {
    Room room = new Room(1L, ROOM_NUMBER, CAPACITY, null);
    RoomDto roomDto = roomMapper.roomToDto(room);

    assertEquals(room.getId(), roomDto.getId());
    assertEquals(room.getName(), roomDto.getName());
    assertEquals(room.getCapacity(), roomDto.getCapacity());
  }

  @Test
  public void Should_ConvertDtoToRoom_When_DtoHasId() {
    RoomDto roomDto = new RoomDto(1L, ROOM_NUMBER, CAPACITY);
    Room room = roomMapper.dtoToRoom(roomDto);

    assertEquals(roomDto.getId(), room.getId());
    assertEquals(roomDto.getName(), room.getName());
    assertEquals(roomDto.getCapacity(), room.getCapacity());
  }

  @Test
  public void Should_ConvertDtoToRoom_When_DtoHasNotId() {
    RoomDto roomDto = new RoomDto(null, ROOM_NUMBER, CAPACITY);
    Room room = roomMapper.dtoToRoom(roomDto);

    assertNull(room.getId());
    assertEquals(roomDto.getName(), room.getName());
    assertEquals(roomDto.getCapacity(), room.getCapacity());
  }

}
