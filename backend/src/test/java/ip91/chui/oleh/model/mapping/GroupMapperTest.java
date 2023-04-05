package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.Group;
import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.Shift;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroupMapperTest {

  private static final String LETTER_A = "A";

  @Mock
  private GroupLimitsMapper groupLimitsMapper;

  @InjectMocks
  private final GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

  @Test
  void Should_ConvertGroupToDto_When_GroupIsValid() {
    Group group = new Group(1L, GradeNumber.FIRST, LETTER_A, Shift.FIRST, null, null);

    GroupDto groupDto = groupMapper.groupToDto(group);

    assertEquals(group.getId(), groupDto.getId());
    assertEquals(group.getGradeNumber(), groupDto.getGradeNumber());
    assertEquals(group.getLetter(), groupDto.getLetter());
    assertEquals(group.getShift(), groupDto.getShift());
    assertNull(groupDto.getLessonDtoSet());
    assertNull(groupDto.getGroupLimitsDto());
  }

  @Test
  void Should_ConvertDtoToGroup_When_DtoHasId() {
    GroupDto groupDto = new GroupDto(1L, GradeNumber.FIRST, LETTER_A, Shift.FIRST, null, null);

    Group group = groupMapper.dtoToGroup(groupDto);

    assertEquals(groupDto.getId(), group.getId());
    assertEquals(groupDto.getGradeNumber(), group.getGradeNumber());
    assertEquals(groupDto.getLetter(), group.getLetter());
    assertEquals(groupDto.getShift(), group.getShift());
    assertNull(groupDto.getLessonDtoSet());
    assertNull(groupDto.getGroupLimitsDto());
  }

  @Test
  void Should_ConvertDtoToGroup_When_DtoHasNotId() {
    GroupDto groupDto = new GroupDto(null, GradeNumber.FIRST, LETTER_A, Shift.FIRST, null, null);

    Group group = groupMapper.dtoToGroup(groupDto);

    assertNull(group.getId());
    assertEquals(groupDto.getGradeNumber(), group.getGradeNumber());
    assertEquals(groupDto.getLetter(), group.getLetter());
    assertEquals(groupDto.getShift(), group.getShift());
    assertNull(groupDto.getLessonDtoSet());
    assertNull(groupDto.getGroupLimitsDto());
  }

}
