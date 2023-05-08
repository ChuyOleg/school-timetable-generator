package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroupMapperTest {

  private static final int GRADE_NUMBER_7 = 7;
  private static final String LETTER_A = "A";
  private static final int SHIFT_1 = 1;

  @Mock
  private TeacherMapper teacherMapper;

  @Mock
  private GroupLimitsMapper groupLimitsMapper;

  @InjectMocks
  private final GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

  @Test
  void Should_ConvertGroupToDto_When_GroupIsValid() {
    Group group = new Group(1L, GRADE_NUMBER_7, LETTER_A, SHIFT_1, null, null, null, LocalDateTime.now(), LocalDateTime.now());

    GroupDto groupDto = groupMapper.groupToDto(group);

    assertEquals(group.getId(), groupDto.getId());
    assertEquals(group.getGradeNumber(), groupDto.getGradeNumber());
    assertEquals(group.getLetter(), groupDto.getLetter());
    assertEquals(group.getShift(), groupDto.getShift());
    assertNull(groupDto.getGroupLimitsDto());
  }

  @Test
  void Should_ConvertDtoToGroup_When_DtoHasId() {
    GroupDto groupDto = new GroupDto(1L, GRADE_NUMBER_7, LETTER_A, SHIFT_1, null, null, null);

    Group group = groupMapper.dtoToGroup(groupDto);

    assertEquals(groupDto.getId(), group.getId());
    assertEquals(groupDto.getGradeNumber(), group.getGradeNumber());
    assertEquals(groupDto.getLetter(), group.getLetter());
    assertEquals(groupDto.getShift(), group.getShift());
    assertNull(groupDto.getGroupLimitsDto());
  }

  @Test
  void Should_ConvertDtoToGroup_When_DtoHasNotId() {
    GroupDto groupDto = new GroupDto(null, GRADE_NUMBER_7, LETTER_A, SHIFT_1, null, null, null);

    Group group = groupMapper.dtoToGroup(groupDto);

    assertNull(group.getId());
    assertEquals(groupDto.getGradeNumber(), group.getGradeNumber());
    assertEquals(groupDto.getLetter(), group.getLetter());
    assertEquals(groupDto.getShift(), group.getShift());
    assertNull(groupDto.getGroupLimitsDto());
  }

}
