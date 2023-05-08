package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class })
public interface TeacherMapper {

  @Named("teacherToDto")
  @Mapping(target = "subjectDtoSet", source = "subjects")
  TeacherDto teacherToDto(Teacher teacher);

  @Mapping(target = "subjects", source = "subjectDtoSet")
  Teacher dtoToTeacher(TeacherDto teacherDto);

  @Named("teacherToDtoWithoutSubjects")
  TeacherDto teacherToDtoWithoutSubjects(Teacher teacher);

}
