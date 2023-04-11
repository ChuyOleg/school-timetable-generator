package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectLimitsDto;
import ip91.chui.oleh.model.entity.SubjectLimits;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class, TeacherMapper.class, RoomMapper.class })
public interface SubjectLimitsMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "subjectDto", source = "subject"),
      @Mapping(target = "hours", source = "hours"),
      @Mapping(target = "teacherDto", source = "teacher", qualifiedByName = {"teacherToDto"}),
      @Mapping(target = "roomDto", source = "room"),
      @Mapping(target = "teacherDto2", source = "teacher2", qualifiedByName = {"teacherToDto"}),
      @Mapping(target = "roomDto2", source = "room2")
  })
  SubjectLimitsDto SubjectLimitsToDto(SubjectLimits subjectLimits);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "subject", source = "subjectDto"),
      @Mapping(target = "hours", source = "hours"),
      @Mapping(target = "teacher", source = "teacherDto"),
      @Mapping(target = "room", source = "roomDto"),
      @Mapping(target = "teacher2", source = "teacherDto2"),
      @Mapping(target = "room2", source = "roomDto2")
  })
  SubjectLimits dtoToSubjectLimits(SubjectLimitsDto subjectLimitsDto);

}
