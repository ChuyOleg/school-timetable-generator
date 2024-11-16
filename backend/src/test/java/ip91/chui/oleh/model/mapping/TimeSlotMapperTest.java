package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.entity.TimeSlot;
import ip91.chui.oleh.model.enumeration.WeekType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

final class TimeSlotMapperTest {

  private static final int LESSON_NUMBER_2 = 2;
  private static final int SHIFT = 1;

  private final TimeSlotMapper timeSlotMapper = Mappers.getMapper(TimeSlotMapper.class);

  @Test
  void shouldProperlyMapToDto() {
    TimeSlot timeSlot = new TimeSlot(1L, LESSON_NUMBER_2, DayOfWeek.FRIDAY, SHIFT, WeekType.EVEN);
    TimeSlotDto timeSlotDto = timeSlotMapper.timeSlotToDto(timeSlot);

    assertNotNull(timeSlotDto);
    assertEquals(timeSlot.getId(), timeSlotDto.getId());
    assertEquals(timeSlot.getLessonNumber(), timeSlotDto.getLessonNumber());
    assertEquals(timeSlot.getDay(), timeSlotDto.getDay());
    assertEquals(timeSlot.getShift(), timeSlotDto.getShift());
    assertEquals(timeSlot.getWeekType(), timeSlotDto.getWeekType());
  }

  @Test
  void shouldProperlyMapToEntity() {
    TimeSlotDto timeSlotDto = new TimeSlotDto(1L, LESSON_NUMBER_2, DayOfWeek.WEDNESDAY, SHIFT, WeekType.ODD);
    TimeSlot timeSlot = timeSlotMapper.dtoToTimeSlot(timeSlotDto);

    assertNotNull(timeSlot);
    assertEquals(timeSlotDto.getId(), timeSlot.getId());
    assertEquals(timeSlotDto.getLessonNumber(), timeSlot.getLessonNumber());
    assertEquals(timeSlotDto.getDay(), timeSlot.getDay());
    assertEquals(timeSlotDto.getShift(), timeSlot.getShift());
    assertEquals(timeSlotDto.getWeekType(), timeSlot.getWeekType());
  }

  @Test
  void shouldProperlyMapToEntityWhenDtoHasNotId() {
    TimeSlotDto timeSlotDto = new TimeSlotDto(null, LESSON_NUMBER_2, DayOfWeek.WEDNESDAY, SHIFT, WeekType.ODD);
    TimeSlot timeSlot = timeSlotMapper.dtoToTimeSlot(timeSlotDto);

    assertNotNull(timeSlot);
    assertNull(timeSlot.getId());
    assertEquals(timeSlotDto.getLessonNumber(), timeSlot.getLessonNumber());
    assertEquals(timeSlotDto.getDay(), timeSlot.getDay());
    assertEquals(timeSlotDto.getShift(), timeSlot.getShift());
    assertEquals(timeSlotDto.getWeekType(), timeSlot.getWeekType());
  }

}
