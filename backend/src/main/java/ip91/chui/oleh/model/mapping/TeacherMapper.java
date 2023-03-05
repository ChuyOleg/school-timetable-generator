package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class })
public interface TeacherMapper {

  @Mapping(target = "subjectDtoSet", source = "subjects")
  TeacherDto teacherToDto(Teacher teacher);

  @Mapping(target = "subjects", source = "subjectDtoSet")

  Teacher dtoToTeacher(TeacherDto teacherDto);

}
