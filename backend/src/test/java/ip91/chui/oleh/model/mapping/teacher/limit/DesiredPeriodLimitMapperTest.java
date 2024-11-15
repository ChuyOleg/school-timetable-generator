package ip91.chui.oleh.model.mapping.teacher.limit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.teacher.limit.DesiredPeriodLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.DesiredPeriodLimit;
import java.time.DayOfWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

final class DesiredPeriodLimitMapperTest {

  private static final Long ID = 1L;
  private static final DayOfWeek DAY = DayOfWeek.TUESDAY;
  private static final int SHIFT = 1;
  private static final int LESSON_FROM = 3;
  private static final int LESSON_TO = 6;

  private DesiredPeriodLimitMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = Mappers.getMapper(DesiredPeriodLimitMapper.class);
  }

  @Test
  void shouldProperlyMapToDto() {
    var limit = new DesiredPeriodLimit(ID, DAY, SHIFT, LESSON_FROM, LESSON_TO);
    var limitDto = mapper.toDto(limit);

    assertEquals(ID, limitDto.getId());
    assertEquals(DAY, limitDto.getDay());
    assertEquals(SHIFT, limitDto.getShift());
    assertEquals(LESSON_FROM, limitDto.getLessonFrom());
    assertEquals(LESSON_TO, limitDto.getLessonTo());
  }

  @Test
  void shouldProperlyMapToEntity() {
    var limitDto = new DesiredPeriodLimitDto(ID, DAY, SHIFT, LESSON_FROM, LESSON_TO);
    var limit = mapper.toEntity(limitDto);

    assertEquals(ID, limit.getId());
    assertEquals(DAY, limit.getDay());
    assertEquals(SHIFT, limit.getShift());
    assertEquals(LESSON_FROM, limit.getLessonFrom());
    assertEquals(LESSON_TO, limit.getLessonTo());
  }
}