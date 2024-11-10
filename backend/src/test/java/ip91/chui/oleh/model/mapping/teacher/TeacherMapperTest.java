package ip91.chui.oleh.model.mapping.teacher;

import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.entity.teacher.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;


import java.time.LocalDateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
final class TeacherMapperTest {

  private static final String JOHN_DOE = "John Doe";
  private static final int MAX_HOURS_PER_WEEK = 20;

  @Mock
  private TeacherLimitsMapper teacherLimitsMapper;

  @InjectMocks
  private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

  @Test
  void Should_ConvertTeacherToDto_When_TeacherIsValid() {
    Teacher teacher = new Teacher(1L, JOHN_DOE, MAX_HOURS_PER_WEEK, null, null, null, LocalDateTime.now(), LocalDateTime.now());

    TeacherDto teacherDto = teacherMapper.teacherToDto(teacher);

    assertEquals(teacher.getId(), teacherDto.getId());
    assertEquals(teacher.getName(), teacherDto.getName());
    assertNull(teacherDto.getSubjectDtoSet());
    assertEquals(teacher.getMaxHoursPerWeek(), teacherDto.getMaxHoursPerWeek());
  }

  @Test
  void Should_ConvertDtoToTeacher_When_DtoHasId() {
    TeacherDto teacherDto = new TeacherDto(1L, JOHN_DOE, null, MAX_HOURS_PER_WEEK, null);
    Teacher teacher = teacherMapper.dtoToTeacher(teacherDto);

    assertEquals(1L, teacher.getId());
    assertEquals(teacherDto.getName(), teacher.getName());
    assertNull(teacher.getSubjects());
    assertEquals(teacherDto.getMaxHoursPerWeek(), teacher.getMaxHoursPerWeek());
  }

  @Test
  void Should_ConvertDtoToTeacher_When_DtoHasNotId() {
    TeacherDto teacherDto = new TeacherDto(null, JOHN_DOE, null, MAX_HOURS_PER_WEEK, null);
    Teacher teacher = teacherMapper.dtoToTeacher(teacherDto);

    assertNull(teacher.getId());
    assertEquals(teacherDto.getName(), teacher.getName());
    assertNull(teacher.getSubjects());
    assertEquals(teacherDto.getMaxHoursPerWeek(), teacher.getMaxHoursPerWeek());
  }
}
