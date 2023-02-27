package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.TeacherDtoValidationException;
import ip91.chui.oleh.exception.TeacherProcessingException;
import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.entity.Teacher;
import ip91.chui.oleh.model.mapping.SubjectMapper;
import ip91.chui.oleh.model.mapping.TeacherMapper;
import ip91.chui.oleh.repository.TeacherRepository;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private static final String TEACHER_NOT_FOUND_BY_ID = "Teacher hasn't been found with ID = (%d)";
  private static final String TEACHER_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the teacher";

  private final TeacherRepository teacherRepository;
  private final TeacherMapper teacherMapper;
  private final SubjectMapper subjectMapper;
  private final DtoValidator<TeacherDto> teacherDtoValidator;

  public List<TeacherDto> getAll() {
    return teacherRepository.findAll()
        .stream()
        .map(teacherMapper::teacherToDto)
        .collect(Collectors.toList());
  }

  public TeacherDto getById(Long id) {
    return teacherMapper.teacherToDto(findTeacherById(id));
  }

  public TeacherDto create(TeacherDto teacherDto) {
    validateTeacherDto(teacherDto);

    Teacher teacherToSave = teacherMapper.dtoToTeacher(teacherDto);
    Teacher savedTeacher = teacherRepository.save(teacherToSave);

    return teacherMapper.teacherToDto(savedTeacher);
  }

  public TeacherDto update(TeacherDto teacherDto) {
    validateIdIsNotNull(teacherDto);
    validateTeacherDto(teacherDto);

    Teacher teacherToUpdate = findTeacherById(teacherDto.getId());
    teacherToUpdate.setName(teacherDto.getName());
    teacherToUpdate.setSubjects(teacherDto.getSubjects()
        .stream()
        .map(subjectMapper::dtoToSubject)
        .collect(Collectors.toSet()));
    teacherToUpdate.setMaxHoursPerWeek(teacherDto.getMaxHoursPerWeek());

    Teacher updatedTeacher = teacherRepository.save(teacherToUpdate);

    return teacherMapper.teacherToDto(updatedTeacher);
  }

  public void delete(Long id) {
    Teacher teacher = findTeacherById(id);

    teacherRepository.deleteById(teacher.getId());
  }

  private Teacher findTeacherById(Long id) {
    return teacherRepository.findById(id)
        .orElseThrow(() -> new TeacherProcessingException(String.format(TEACHER_NOT_FOUND_BY_ID, id)));
  }

  private void validateTeacherDto(TeacherDto teacherDto) {
    Set<String> violations = teacherDtoValidator.validate(teacherDto);
    if (!violations.isEmpty()) {
      throw new TeacherDtoValidationException(violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }
  }

  private void validateIdIsNotNull(TeacherDto teacherDto) {
    if (teacherDto.getId() == null) {
      throw new TeacherDtoValidationException(TEACHER_ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }

}
