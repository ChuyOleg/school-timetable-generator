package ip91.chui.oleh.model.mapping.teacher.limit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.teacher.limit.LessonsOrderLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.LessonsOrderLimit;
import ip91.chui.oleh.model.enumeration.ImportanceLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

final class LessonsOrderLimitMapperTest {

  private static final Long ID = 1L;
  private static final ImportanceLevel IMPORTANCE_LEVEL = ImportanceLevel.MEDIUM;

  private LessonsOrderLimitMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = Mappers.getMapper(LessonsOrderLimitMapper.class);
  }

  @Test
  void shouldProperlyMapToDto() {
    var limit = new LessonsOrderLimit(ID, IMPORTANCE_LEVEL, null);
    var limitDto = mapper.toDto(limit);

    assertEquals(ID, limitDto.getId());
    assertEquals(IMPORTANCE_LEVEL, limitDto.getImportanceLevel());
  }

  @Test
  void shouldProperlyMapToEntity() {
    var limitDto = new LessonsOrderLimitDto(ID, IMPORTANCE_LEVEL);
    var limit = mapper.toEntity(limitDto);

    assertEquals(ID, limit.getId());
    assertEquals(IMPORTANCE_LEVEL, limit.getImportanceLevel());
  }
}