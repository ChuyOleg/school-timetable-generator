package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupLimitsDto;
import ip91.chui.oleh.model.entity.GroupLimits;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { SubjectLimitsMapper.class })
public interface GroupLimitsMapper {

  @Named("groupLimitsToDto")
  @Mappings({
      @Mapping(target = "subjectLimitsDtoSet", source = "subjectLimitsSet"),
  })
  GroupLimitsDto groupLimitsToDto(GroupLimits groupLimits);

  @Mappings({
      @Mapping(target = "subjectLimitsSet", source = "subjectLimitsDtoSet"),
  })
  GroupLimits dtoToGroupLimits(GroupLimitsDto groupLimitsDto);

  @Named("groupLimitsToDtoLimitedInfo")
  GroupLimitsDto groupLimitsToDtoLimitedInfo(GroupLimits groupLimits);

  @AfterMapping
  default void setGroupLimitsForSubjectLimitsSet(@MappingTarget GroupLimits groupLimits) {
    if (groupLimits.getSubjectLimitsSet() != null) {
      groupLimits.getSubjectLimitsSet().forEach(s -> s.setGroupLimits(groupLimits));
    }
  }

}
