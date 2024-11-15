package ip91.chui.oleh.model.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.ShiftsIntersectionDto;
import ip91.chui.oleh.model.entity.ShiftsIntersection;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

final class ShiftsIntersectionMapperTest {

  private static final Long ID = 1L;
  private static final int SHIFT_ONE_LESSON_NUMBER = 7;
  private static final int SHIFT_TWO_LESSON_NUMBER = 1;

  private final ShiftsIntersectionMapper mapper = Mappers.getMapper(ShiftsIntersectionMapper.class);

  @Test
  void shouldProperlyMapToDto() {
    var entity = new ShiftsIntersection(ID, SHIFT_ONE_LESSON_NUMBER, SHIFT_TWO_LESSON_NUMBER, null);
    var dto = mapper.toDto(entity);

    assertEquals(ID, dto.getId());
    assertEquals(SHIFT_ONE_LESSON_NUMBER, dto.getShiftOneLessonNumber());
    assertEquals(SHIFT_TWO_LESSON_NUMBER, dto.getShiftTwoLessonNumber());
  }

  @Test
  void shouldProperlyMapToEntity() {
    var dto = new ShiftsIntersectionDto(ID, SHIFT_ONE_LESSON_NUMBER, SHIFT_TWO_LESSON_NUMBER);
    var entity = mapper.toEntity(dto);

    assertEquals(ID, entity.getId());
    assertEquals(SHIFT_ONE_LESSON_NUMBER, entity.getShiftOneLessonNumber());
    assertEquals(SHIFT_TWO_LESSON_NUMBER, entity.getShiftTwoLessonNumber());
  }
}