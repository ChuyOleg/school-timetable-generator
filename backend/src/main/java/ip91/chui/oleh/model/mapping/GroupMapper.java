package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.Group;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { GroupLimitsMapper.class, LessonMapper.class, TeacherMapper.class })
public interface GroupMapper {

  @Mappings({
      @Mapping(target = "lessonDtoSet", source = "lessonSet"),
      @Mapping(target = "groupLimitsDto", source = "groupLimits"),
      @Mapping(target = "teacherDto", source = "teacher")
  })
  GroupDto groupToDto(Group group);

  @Mappings({
      @Mapping(target = "lessonSet", source = "lessonDtoSet"),
      @Mapping(target = "groupLimits", source = "groupLimitsDto"),
      @Mapping(target = "teacher", source = "teacherDto")
  })
  Group dtoToGroup(GroupDto groupDto);

  @AfterMapping
  default void setGroupForGroupLimits(@MappingTarget Group group) {
    if (group.getGroupLimits() != null) {
      group.getGroupLimits().setGroup(group);
    }
  }

}
