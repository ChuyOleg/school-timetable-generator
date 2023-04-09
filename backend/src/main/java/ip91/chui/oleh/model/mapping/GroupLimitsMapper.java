package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupLimitsDto;
import ip91.chui.oleh.model.entity.GroupLimits;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { SubjectHoursInGroupMapper.class, SubjectTeacherRoomInGroupMapper.class })
public interface GroupLimitsMapper {

  @Named("groupLimitsToDto")
  @Mappings({
      @Mapping(target = "subjectTeacherRoomInGroupDtoSet", source = "subjectTeacherRoomInGroupSet"),
      @Mapping(target = "subjectHoursInGroupDtoSet", source = "subjectHoursInGroupSet")
  })
  GroupLimitsDto groupLimitsToDto(GroupLimits groupLimits);

  @Mappings({
      @Mapping(target = "subjectTeacherRoomInGroupSet", source = "subjectTeacherRoomInGroupDtoSet"),
      @Mapping(target = "subjectHoursInGroupSet", source = "subjectHoursInGroupDtoSet")
  })
  GroupLimits dtoToGroupLimits(GroupLimitsDto groupLimitsDto);

  @Named("groupLimitsToDtoLimitedInfo")
  @Mapping(target = "interschoolCombine", ignore = true)
  GroupLimitsDto groupLimitsToDtoLimitedInfo(GroupLimits groupLimits);

  @AfterMapping
  default void setGroupLimitsForSubjectTeacherInGroupSet(@MappingTarget GroupLimits groupLimits) {
    if (groupLimits.getSubjectTeacherRoomInGroupSet() != null) {
      groupLimits.getSubjectTeacherRoomInGroupSet().forEach(s -> s.setGroupLimits(groupLimits));
    }
  }

  @AfterMapping
  default void setGroupLimitsForSubjectHoursInGroupSet(@MappingTarget GroupLimits groupLimits) {
    if (groupLimits.getSubjectHoursInGroupSet() != null) {
      groupLimits.getSubjectHoursInGroupSet().forEach(s -> s.setGroupLimits(groupLimits));
    }
  }

}
