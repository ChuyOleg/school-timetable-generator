package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.entity.TimeSlot;
import ip91.chui.oleh.model.enumeration.LessonNumber;
import ip91.chui.oleh.model.enumeration.WeekType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotMapperTest {

  private final TimeSlotMapper timeSlotMapper = Mappers.getMapper(TimeSlotMapper.class);

  @Test
  void Should_ConvertTimeSlotToDto_When_TimeSlotIsValid() {
    TimeSlot timeSlot = new TimeSlot(1L, LessonNumber.SECOND, DayOfWeek.FRIDAY, WeekType.EVEN);
    TimeSlotDto timeSlotDto = timeSlotMapper.timeSlotToDto(timeSlot);

    assertNotNull(timeSlotDto);
    assertEquals(timeSlot.getId(), timeSlotDto.getId());
    assertEquals(timeSlot.getLessonNumber(), timeSlotDto.getLessonNumber());
    assertEquals(timeSlot.getDay(), timeSlotDto.getDay());
    assertEquals(timeSlot.getWeekType(), timeSlotDto.getWeekType());
  }

  @Test
  void Should_ConvertDtoToTimeSlot_When_DtoHasId() {
    TimeSlotDto timeSlotDto = new TimeSlotDto(1L, LessonNumber.SEVENTH, DayOfWeek.WEDNESDAY, WeekType.ODD);
    TimeSlot timeSlot = timeSlotMapper.dtoToTimeSlot(timeSlotDto);

    assertNotNull(timeSlot);
    assertEquals(timeSlotDto.getId(), timeSlot.getId());
    assertEquals(timeSlotDto.getLessonNumber(), timeSlot.getLessonNumber());
    assertEquals(timeSlotDto.getDay(), timeSlot.getDay());
    assertEquals(timeSlotDto.getWeekType(), timeSlot.getWeekType());
  }

  @Test
  void Should_ConvertDtoToTimeSlot_When_DtoHasNotId() {
    TimeSlotDto timeSlotDto = new TimeSlotDto(null, LessonNumber.SEVENTH, DayOfWeek.WEDNESDAY, WeekType.ODD);
    TimeSlot timeSlot = timeSlotMapper.dtoToTimeSlot(timeSlotDto);

    assertNotNull(timeSlot);
    assertNull(timeSlot.getId());
    assertEquals(timeSlotDto.getLessonNumber(), timeSlot.getLessonNumber());
    assertEquals(timeSlotDto.getDay(), timeSlot.getDay());
    assertEquals(timeSlotDto.getWeekType(), timeSlot.getWeekType());
  }

}