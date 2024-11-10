package ip91.chui.oleh.model.mapping.teacher;

import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.entity.teacher.Teacher;
import ip91.chui.oleh.model.mapping.SubjectMapper;
import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class, TeacherLimitsMapper.class })
public interface TeacherMapper {

  @Named("teacherToDto")
  @Mapping(target = "subjectDtoSet", source = "subjects")
  TeacherDto teacherToDto(Teacher teacher);

  @Mapping(target = "subjects", source = "subjectDtoSet")
  Teacher dtoToTeacher(TeacherDto teacherDto);

  @Named("teacherToDtoWithoutSubjects")
  TeacherDto teacherToDtoWithoutSubjects(Teacher teacher);

  @AfterMapping
  default void setReverseDependency(@MappingTarget Teacher teacher) {
    Optional.ofNullable(teacher.getLimits())
        .ifPresent(limit -> limit.setTeacher(teacher));
  }
}
