package ip91.chui.oleh.model.mapping.teacher.limit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.teacher.limit.FreeDayLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.FreeDayLimit;
import java.time.DayOfWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

final class FreeDayLimitMapperTest {

  private static final Long ID = 1L;
  private static final DayOfWeek DAY = DayOfWeek.TUESDAY;

  private FreeDayLimitMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = Mappers.getMapper(FreeDayLimitMapper.class);
  }

  @Test
  void shouldProperlyMapToDto() {
    var limit = new FreeDayLimit(ID, DayOfWeek.TUESDAY);
    var limitDto = mapper.toDto(limit);

    assertEquals(ID, limitDto.getId());
    assertEquals(DAY, limitDto.getDay());
  }

  @Test
  void shouldProperlyMapToEntity() {
    var limitDto = new FreeDayLimitDto(ID, DAY);
    var limit = mapper.toEntity(limitDto);

    assertEquals(ID, limit.getId());
    assertEquals(DAY, limit.getDay());
  }
}