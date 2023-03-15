package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.entity.Subject;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class SubjectMapperTest {

  private static final String MATH = "Math";

  private final SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);

  @Test
  void Should_ConvertSubjectToDto_When_SubjectIsValid() {
    Subject subject = new Subject(1L, MATH);
    SubjectDto subjectDto = subjectMapper.subjectToDto(subject);

    assertNotNull(subjectDto);
    assertEquals(subject.getId(), subjectDto.getId());
    assertEquals(subject.getName(), subjectDto.getName());
  }

  @Test
  void Should_ConvertDtoToSubject_When_DtoHasId() {
    SubjectDto subjectDto = new SubjectDto(1L, MATH);
    Subject subject = subjectMapper.dtoToSubject(subjectDto);

    assertNotNull(subject);
    assertEquals(subjectDto.getId(), subject.getId());
    assertEquals(subjectDto.getName(), subject.getName());
  }

  @Test
  void Should_ConvertDtoToSubject_When_DtoHasNotId() {
    SubjectDto subjectDto = new SubjectDto(null, MATH);
    Subject subject = subjectMapper.dtoToSubject(subjectDto);

    assertNotNull(subject);
    assertNull(subject.getId());
    assertEquals(subjectDto.getName(), subject.getName());
  }

}
