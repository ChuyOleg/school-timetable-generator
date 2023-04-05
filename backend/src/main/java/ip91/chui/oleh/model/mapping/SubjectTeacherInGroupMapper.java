package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectTeacherInGroupDto;
import ip91.chui.oleh.model.entity.SubjectTeacherInGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class, TeacherMapper.class })
public interface SubjectTeacherInGroupMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "subjectDto", source = "subject"),
      @Mapping(target = "teacherDto", source = "teacher")
  })
  SubjectTeacherInGroupDto SubjectTeacherInGroupToDto(SubjectTeacherInGroup subjectTeacherInGroup);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "subject", source = "subjectDto"),
      @Mapping(target = "teacher", source = "teacherDto")
  })
  SubjectTeacherInGroup dtoToSubjectTeacherInGroup(SubjectTeacherInGroupDto subjectTeacherInGroupDto);

}
