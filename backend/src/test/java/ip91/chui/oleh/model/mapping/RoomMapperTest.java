package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.room.RoomLimitDto;
import ip91.chui.oleh.model.entity.room.Room;
import ip91.chui.oleh.model.entity.room.RoomLimit;
import java.time.DayOfWeek;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RoomMapperTest {

  private static final Long ROOM_ID = 1L;
  private static final String ROOM_NUMBER = "Room №1";
  private static final int CAPACITY = 30;

  private final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

  @Test
  public void Should_ConvertRoomToDto_When_RoomIsValid() {
    RoomLimit roomLimit = new RoomLimit(10L, null, DayOfWeek.MONDAY, 1, 3, 6);
    Set<RoomLimit> roomLimits = Set.of(roomLimit);

    Room room = new Room(ROOM_ID, ROOM_NUMBER, CAPACITY, roomLimits, null, LocalDateTime.now(), LocalDateTime.now());
    RoomDto roomDto = roomMapper.roomToDto(room);

    assertEquals(room.getId(), roomDto.getId());
    assertEquals(room.getName(), roomDto.getName());
    assertEquals(room.getCapacity(), roomDto.getCapacity());
    assertThat(roomDto.getRoomLimitDtoSet()).isNotEmpty();
  }

  @Test
  public void Should_ConvertDtoToRoom_When_DtoHasId() {
    RoomLimitDto roomLimitDto = new RoomLimitDto(10L, DayOfWeek.THURSDAY, 1, 3, 4);
    Set<RoomLimitDto> roomLimitsDto = Set.of(roomLimitDto);

    RoomDto roomDto = new RoomDto(ROOM_ID, ROOM_NUMBER, CAPACITY, roomLimitsDto);
    Room room = roomMapper.dtoToRoom(roomDto);

    assertEquals(roomDto.getId(), room.getId());
    assertEquals(roomDto.getName(), room.getName());
    assertEquals(roomDto.getCapacity(), room.getCapacity());
    assertThat(room.getRoomLimitSet()).isNotEmpty();
    assertTrue(room.getRoomLimitSet().stream().allMatch(limit -> room.equals(limit.getRoom())));
  }

  @Test
  public void Should_ConvertDtoToRoom_When_DtoHasNotId() {
    RoomDto roomDto = new RoomDto(null, ROOM_NUMBER, CAPACITY, null);
    Room room = roomMapper.dtoToRoom(roomDto);

    assertNull(room.getId());
    assertEquals(roomDto.getName(), room.getName());
    assertEquals(roomDto.getCapacity(), room.getCapacity());
  }
}
