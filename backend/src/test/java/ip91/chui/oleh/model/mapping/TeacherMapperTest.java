package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeacherMapperTest {

  private final static String JOHN_DOE = "John Doe";

  private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

  @Test
  void testMapTeacherToDto() {
    Teacher teacher = new Teacher(1L, JOHN_DOE, null, 20);

    TeacherDto teacherDto = teacherMapper.teacherToDto(teacher);

    assertEquals(teacher.getId(), teacherDto.getId());
    assertEquals(teacher.getName(), teacherDto.getName());
    assertNull(teacherDto.getSubjects());
    assertEquals(teacher.getMaxHoursPerWeek(), teacherDto.getMaxHoursPerWeek());
  }

  @Test
  void testMapDtoToTeacherWithId() {
    TeacherDto teacherDto = new TeacherDto(1L, JOHN_DOE, null, 20);
    Teacher teacher = teacherMapper.dtoToTeacher(teacherDto);

    assertEquals(1L, teacher.getId());
    assertEquals(teacherDto.getName(), teacher.getName());
    assertNull(teacher.getSubjects());
    assertEquals(teacherDto.getMaxHoursPerWeek(), teacher.getMaxHoursPerWeek());
  }

  @Test
  void testMapDtoToTeacherWithoutId() {
    TeacherDto teacherDto = new TeacherDto(null, JOHN_DOE, null, 20);
    Teacher teacher = teacherMapper.dtoToTeacher(teacherDto);

    assertNull(teacher.getId());
    assertEquals(teacherDto.getName(), teacher.getName());
    assertNull(teacher.getSubjects());
    assertEquals(teacherDto.getMaxHoursPerWeek(), teacher.getMaxHoursPerWeek());
  }

  @Test
  void testDtoToTeacherWithNull() {
    Teacher teacher = teacherMapper.dtoToTeacher(null);
    assertNull(teacher);
  }

}
