package ip91.chui.oleh.model.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.room.RoomLimitDto;
import ip91.chui.oleh.model.entity.room.RoomLimit;
import java.time.DayOfWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

final class RoomLimitsMapperTest {

  private static final Long ROOM_LIMIT_ID = 1L;

  private RoomLimitsMapper roomLimitsMapper;

  @BeforeEach
  void setUp() {
    this.roomLimitsMapper = Mappers.getMapper(RoomLimitsMapper.class);
  }

  @Test
  void should_Scenario_1() {
    RoomLimit roomLimit = new RoomLimit(ROOM_LIMIT_ID, null, DayOfWeek.TUESDAY, 4, 6);
    RoomLimitDto roomLimitDto = roomLimitsMapper.roomLimitToDto(roomLimit);

    assertEquals(roomLimit.getId(), roomLimitDto.getId());
    assertEquals(roomLimit.getDay(), roomLimitDto.getDay());
    assertEquals(roomLimit.getLessonNumberFrom(), roomLimitDto.getLessonNumberFrom());
    assertEquals(roomLimit.getLessonNumberTo(), roomLimitDto.getLessonNumberTo());
  }

  @Test
  void should_Scenario_2() {
    RoomLimitDto roomLimitDto = new RoomLimitDto(ROOM_LIMIT_ID, DayOfWeek.FRIDAY, 5, 7);
    RoomLimit roomLimit = roomLimitsMapper.dtoToRoomLimit(roomLimitDto);

    assertEquals(roomLimitDto.getId(), roomLimit.getId());
    assertEquals(roomLimitDto.getDay(), roomLimit.getDay());
    assertEquals(roomLimitDto.getLessonNumberFrom(), roomLimit.getLessonNumberFrom());
    assertEquals(roomLimitDto.getLessonNumberTo(), roomLimit.getLessonNumberTo());
  }
}