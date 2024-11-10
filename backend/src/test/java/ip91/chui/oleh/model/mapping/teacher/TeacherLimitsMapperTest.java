package ip91.chui.oleh.model.mapping.teacher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.model.dto.teacher.TeacherLimitsDto;
import ip91.chui.oleh.model.entity.teacher.TeacherLimits;
import ip91.chui.oleh.model.mapping.teacher.limit.DesiredPeriodLimitMapper;
import ip91.chui.oleh.model.mapping.teacher.limit.FreeDayLimitMapper;
import ip91.chui.oleh.model.mapping.teacher.limit.LessonsOrderLimitMapper;
import ip91.chui.oleh.model.mapping.teacher.limit.MaxLessonsLimitMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
final class TeacherLimitsMapperTest {

  private static final Long ID = 1L;

  @Mock
  private DesiredPeriodLimitMapper desiredPeriodLimitMapper;
  @Mock
  private FreeDayLimitMapper freeDayLimitMapper;
  @Mock
  private LessonsOrderLimitMapper lessonsOrderLimitMapper;
  @Mock
  private MaxLessonsLimitMapper maxLessonsLimitMapper;

  @InjectMocks
  private TeacherLimitsMapper mapper = Mappers.getMapper(TeacherLimitsMapper.class);

  @Test
  void shouldProperlyMapToDto() {
    var limit = new TeacherLimits(ID, null, null, null, null, null);
    var limitDto = mapper.toDto(limit);

    assertEquals(ID, limitDto.getId());
  }

  @Test
  void shouldProperlyMapToEntity() {
    var limitDto = new TeacherLimitsDto(ID, null, null, null, null);
    var limit = mapper.toEntity(limitDto);

    assertEquals(ID, limit.getId());
  }
}