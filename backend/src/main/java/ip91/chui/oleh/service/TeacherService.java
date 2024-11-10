package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.TeacherDtoValidationException;
import ip91.chui.oleh.exception.TeacherProcessingException;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.projection.TeacherProjection;
import ip91.chui.oleh.model.entity.teacher.Teacher;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.teacher.TeacherMapper;
import ip91.chui.oleh.repository.teacher.TeacherRepository;
import ip91.chui.oleh.repository.teacher.limit.MaxLessonsLimitRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherService {

  private static final String TEACHER_NOT_FOUND_BY_ID = "Teacher hasn't been found with ID = (%d)";
  private static final String TEACHER_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the teacher";

  private final AuthenticationService authService;
  private final TeacherRepository teacherRepository;
  private final MaxLessonsLimitRepository maxLessonsLimitRepository;
  private final TeacherMapper teacherMapper;
  private final DtoValidator<TeacherDto> teacherDtoValidator;

  public List<TeacherDto> getAll() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return teacherRepository.findAllByUserId(user.getId())
        .stream()
        .map(teacherMapper::teacherToDto)
        .collect(Collectors.toList());
  }

  public List<TeacherDto> getAllWhoAreNotClassTeacher() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return teacherRepository.findAllWhoAreNotClassTeacher(user.getId())
        .stream()
        .map(teacherMapper::teacherToDto)
        .collect(Collectors.toList());
  }

  public List<TeacherProjection> getTeachersActualHours() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return teacherRepository.findActualHoursForTeachersByUserId(user.getId());
  }

  public TeacherDto getById(Long id) {
    return teacherMapper.teacherToDto(findTeacherById(id));
  }

  public TeacherDto create(TeacherDto teacherDto) {
    validateTeacherDto(teacherDto);

    User user = authService.extractPrincipalFromSecurityContextHolder();

    Teacher teacherToSave = teacherMapper.dtoToTeacher(teacherDto);
    teacherToSave.setUser(user);
    Teacher savedTeacher = teacherRepository.save(teacherToSave);

    return teacherMapper.teacherToDto(savedTeacher);
  }

  @Transactional
  public TeacherDto update(TeacherDto teacherDto) {
    validateIdIsNotNull(teacherDto);
    validateTeacherDto(teacherDto);

    if (teacherRepository.existsById(teacherDto.getId())) {
      Teacher teacherToUpdate = teacherMapper.dtoToTeacher(teacherDto);
      if (teacherToUpdate.getLimits().getMaxLessonsLimit() == null) {
        maxLessonsLimitRepository.deleteByTeacherLimits(teacherToUpdate.getLimits());
      }
      Teacher updatedTeacher = teacherRepository.save(teacherToUpdate);
      return teacherMapper.teacherToDto(updatedTeacher);
    } else {
      throw new TeacherProcessingException(String.format(TEACHER_NOT_FOUND_BY_ID, teacherDto.getId()));
    }
  }

  public void delete(Long id) {
    if (teacherRepository.existsById(id)) {
      teacherRepository.deleteById(id);
    } else {
      throw new TeacherProcessingException(String.format(TEACHER_NOT_FOUND_BY_ID, id));
    }
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
