package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.Group;
import ip91.chui.oleh.model.mapping.teacher.TeacherMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { GroupLimitsMapper.class, TeacherMapper.class })
public interface GroupMapper {

  @Named("groupToDto")
  @Mappings({
      @Mapping(target = "groupLimitsDto", source = "groupLimits", qualifiedByName = {"groupLimitsToDto"}),
      @Mapping(target = "teacherDto", source = "teacher", qualifiedByName = {"teacherToDto"})
  })
  GroupDto groupToDto(Group group);

  @Mappings({
      @Mapping(target = "groupLimits", source = "groupLimitsDto"),
      @Mapping(target = "teacher", source = "teacherDto")
  })
  Group dtoToGroup(GroupDto groupDto);

  @Mappings({
      @Mapping(target = "groupLimitsDto", source = "groupLimits", qualifiedByName = {"groupLimitsToDtoLimitedInfo"}),
      @Mapping(target = "teacherDto", source = "teacher", qualifiedByName = {"teacherToDtoWithoutSubjects"})
  })
  GroupDto groupToDtoLimitedInfo(Group group);

  @AfterMapping
  default void setGroupForGroupLimits(@MappingTarget Group group) {
    if (group.getGroupLimits() != null) {
      group.getGroupLimits().setGroup(group);
    }
  }

}
