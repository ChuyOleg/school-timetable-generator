package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupLimitsDto;
import ip91.chui.oleh.model.entity.GroupLimits;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


import static org.junit.jupiter.api.Assertions.*;

class GroupLimitsMapperTest {

  private static final int MAX_HOURS_PER_WEEK = 20;

  private final GroupLimitsMapper groupLimitsMapper = Mappers.getMapper(GroupLimitsMapper.class);

  @Test
  void Should_ConvertGroupLimitsToDto_When_GroupLimitsAreValid() {
    GroupLimits groupLimits = new GroupLimits(1L, null, null, MAX_HOURS_PER_WEEK, null);

    GroupLimitsDto groupLimitsDto = groupLimitsMapper.groupLimitsToDto(groupLimits);

    assertEquals(groupLimits.getId(), groupLimitsDto.getId());
    assertNull(groupLimitsDto.getSubjectLimitsDtoSet());
    assertEquals(groupLimits.getMaxHoursPerWeek(), groupLimitsDto.getMaxHoursPerWeek());
  }

  @Test
  void Should_ConvertDtoToGroupLimits_When_DtoHasId() {
    GroupLimitsDto groupLimitsDto = new GroupLimitsDto(1L, null,  MAX_HOURS_PER_WEEK, null);

    GroupLimits groupLimits = groupLimitsMapper.dtoToGroupLimits(groupLimitsDto);

    assertEquals(groupLimitsDto.getId(), groupLimits.getId());
    assertNull(groupLimits.getSubjectLimitsSet());
    assertEquals(groupLimitsDto.getMaxHoursPerWeek(), groupLimits.getMaxHoursPerWeek());
  }

  @Test
  void Should_ConvertDtoToGroupLimits_When_DtoHasNotId() {
    GroupLimitsDto groupLimitsDto = new GroupLimitsDto(null, null, MAX_HOURS_PER_WEEK, null);

    GroupLimits groupLimits = groupLimitsMapper.dtoToGroupLimits(groupLimitsDto);

    assertNull(groupLimits.getId());
    assertNull(groupLimits.getSubjectLimitsSet());
    assertEquals(groupLimitsDto.getMaxHoursPerWeek(), groupLimits.getMaxHoursPerWeek());
  }

}
