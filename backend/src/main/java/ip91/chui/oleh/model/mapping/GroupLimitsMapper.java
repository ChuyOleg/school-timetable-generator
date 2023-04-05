package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupLimitsDto;
import ip91.chui.oleh.model.entity.GroupLimits;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { SubjectHoursInGroupMapper.class, SubjectTeacherInGroupMapper.class })
public interface GroupLimitsMapper {

  @Mappings({
      @Mapping(target = "subjectTeacherInGroupDtoSet", source = "subjectTeacherInGroupSet"),
      @Mapping(target = "subjectHoursInGroupDtoSet", source = "subjectHoursInGroupSet")
  })
  GroupLimitsDto groupLimitsToDto(GroupLimits groupLimits);

  @Mappings({
      @Mapping(target = "subjectTeacherInGroupSet", source = "subjectTeacherInGroupDtoSet"),
      @Mapping(target = "subjectHoursInGroupSet", source = "subjectHoursInGroupDtoSet")
  })
  GroupLimits dtoToGroupLimits(GroupLimitsDto groupLimitsDto);

  @AfterMapping
  default void setGroupLimitsForSubjectTeacherInGroupSet(@MappingTarget GroupLimits groupLimits) {
    if (groupLimits.getSubjectTeacherInGroupSet() != null) {
      groupLimits.getSubjectTeacherInGroupSet().forEach(s -> s.setGroupLimits(groupLimits));
    }
  }

  @AfterMapping
  default void setGroupLimitsForSubjectHoursInGroupSet(@MappingTarget GroupLimits groupLimits) {
    if (groupLimits.getSubjectHoursInGroupSet() != null) {
      groupLimits.getSubjectHoursInGroupSet().forEach(s -> s.setGroupLimits(groupLimits));
    }
  }

}
