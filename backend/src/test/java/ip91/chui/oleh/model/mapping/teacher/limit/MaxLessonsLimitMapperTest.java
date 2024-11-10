package ip91.chui.oleh.model.mapping.teacher.limit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.teacher.limit.MaxLessonsLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.MaxLessonsLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

final class MaxLessonsLimitMapperTest {

  private static final Long ID = 1L;
  private static final int COUNT = 5;

  private MaxLessonsLimitMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = Mappers.getMapper(MaxLessonsLimitMapper.class);
  }

  @Test
  void shouldProperlyMapToDto() {
    var limit = new MaxLessonsLimit(ID, COUNT, null);
    var limitDto = mapper.toDto(limit);

    assertEquals(ID, limitDto.getId());
    assertEquals(COUNT, limitDto.getCount());
  }

  @Test
  void shouldProperlyMapToEntity() {
    var limitDto = new MaxLessonsLimitDto(ID, COUNT);
    var limit = mapper.toEntity(limitDto);

    assertEquals(ID, limit.getId());
    assertEquals(COUNT, limit.getCount());
  }
}