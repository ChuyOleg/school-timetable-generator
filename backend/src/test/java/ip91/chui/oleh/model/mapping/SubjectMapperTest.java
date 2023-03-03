package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.entity.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubjectMapperTest {

  private static final String MATH = "Math";

  private final SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);

  @Test
  void testSubjectToDto() {
    Subject subject = new Subject(1L, MATH, null);
    SubjectDto subjectDto = subjectMapper.subjectToDto(subject);

    assertNotNull(subjectDto);
    assertEquals(subjectDto.getId(), subject.getId());
    assertEquals(subjectDto.getName(), subject.getName());
  }

  @Test
  void testDtoToSubjectWithId() {
    SubjectDto subjectDto = new SubjectDto(1L, MATH);
    Subject subject = subjectMapper.dtoToSubject(subjectDto);

    assertNotNull(subject);
    assertEquals(subject.getId(), subjectDto.getId());
    assertEquals(subject.getName(), subjectDto.getName());
  }

  @Test
  void testDtoToSubjectWithoutId() {
    SubjectDto subjectDto = new SubjectDto(null, MATH);
    Subject subject = subjectMapper.dtoToSubject(subjectDto);

    assertNotNull(subject);
    assertNull(subject.getId());
    assertEquals(subject.getName(), subjectDto.getName());
  }

  @Test
  void testDtoToSubjectWithNull() {
    Subject subject = subjectMapper.dtoToSubject(null);
    assertNull(subject);
  }

}