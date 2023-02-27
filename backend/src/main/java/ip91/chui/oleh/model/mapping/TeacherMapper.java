package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class })
@Component
public interface TeacherMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "name", source = "name"),
      @Mapping(target = "subjects", source = "subjects"),
      @Mapping(target = "maxHoursPerWeek", source = "maxHoursPerWeek")
  })
  TeacherDto teacherToDto(Teacher teacher);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "name", source = "name"),
      @Mapping(target = "subjects", source = "subjects"),
      @Mapping(target = "maxHoursPerWeek", source = "maxHoursPerWeek")
  })
  Teacher dtoToTeacher(TeacherDto teacherDto);

}
