package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class })
public interface TeacherMapper {

  TeacherDto teacherToDto(Teacher teacher);

  Teacher dtoToTeacher(TeacherDto teacherDto);

}
