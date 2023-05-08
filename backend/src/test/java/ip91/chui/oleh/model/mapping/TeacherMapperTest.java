package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherMapperTest {

  private static final String JOHN_DOE = "John Doe";
  private static final int MAX_HOURS_PER_WEEK = 20;

  private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

  @Test
  void Should_ConvertTeacherToDto_When_TeacherIsValid() {
    Teacher teacher = new Teacher(1L, JOHN_DOE, null, MAX_HOURS_PER_WEEK, null, LocalDateTime.now(), LocalDateTime.now());

    TeacherDto teacherDto = teacherMapper.teacherToDto(teacher);

    assertEquals(teacher.getId(), teacherDto.getId());
    assertEquals(teacher.getName(), teacherDto.getName());
    assertNull(teacherDto.getSubjectDtoSet());
    assertEquals(teacher.getMaxHoursPerWeek(), teacherDto.getMaxHoursPerWeek());
  }

  @Test
  void Should_ConvertDtoToTeacher_When_DtoHasId() {
    TeacherDto teacherDto = new TeacherDto(1L, JOHN_DOE, null, MAX_HOURS_PER_WEEK);
    Teacher teacher = teacherMapper.dtoToTeacher(teacherDto);

    assertEquals(1L, teacher.getId());
    assertEquals(teacherDto.getName(), teacher.getName());
    assertNull(teacher.getSubjects());
    assertEquals(teacherDto.getMaxHoursPerWeek(), teacher.getMaxHoursPerWeek());
  }

  @Test
  void Should_ConvertDtoToTeacher_When_DtoHasNotId() {
    TeacherDto teacherDto = new TeacherDto(null, JOHN_DOE, null, MAX_HOURS_PER_WEEK);
    Teacher teacher = teacherMapper.dtoToTeacher(teacherDto);

    assertNull(teacher.getId());
    assertEquals(teacherDto.getName(), teacher.getName());
    assertNull(teacher.getSubjects());
    assertEquals(teacherDto.getMaxHoursPerWeek(), teacher.getMaxHoursPerWeek());
  }

}
