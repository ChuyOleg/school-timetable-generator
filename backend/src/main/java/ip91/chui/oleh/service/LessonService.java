package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.LessonDtoValidationException;
import ip91.chui.oleh.exception.LessonProcessingException;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.entity.Lesson;
import ip91.chui.oleh.model.mapping.LessonMapper;
import ip91.chui.oleh.repository.LessonRepository;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

  private static final String LESSON_NOT_FOUND_BY_ID_MSG = "Lesson hasn't been found with ID = (%d)";
  private static final String LESSON_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the lesson";
  private static final String TEACHER_HAS_NO_RIGHT_FOR_SUBJECT_MSG = "The teacher does not have the right to teach this subject";

  private final LessonRepository lessonRepository;
  private final LessonMapper lessonMapper;
  private final DtoValidator<LessonDto> lessonDtoValidator;

  public List<LessonDto> getAll() {
    return lessonRepository.findAll()
        .stream()
        .map(lessonMapper::lessonToDto)
        .collect(Collectors.toList());
  }

  public LessonDto getById(Long id) {
    return lessonMapper.lessonToDto(findLessonById(id));
  }

  public LessonDto create(LessonDto lessonDto) {
    validateLessonDto(lessonDto);

    Lesson lessonToSave = lessonMapper.dtoToLesson(lessonDto);
    Lesson savedLesson = lessonRepository.save(lessonToSave);

    return lessonMapper.lessonToDto(savedLesson);
  }

  public LessonDto update(LessonDto lessonDto) {
    validateIdIsNotNull(lessonDto);
    validateLessonDto(lessonDto);

    if (lessonRepository.existsById(lessonDto.getId())) {
      Lesson lessonToUpdate = lessonMapper.dtoToLesson(lessonDto);
      Lesson updatedLesson = lessonRepository.save(lessonToUpdate);
      return lessonMapper.lessonToDto(updatedLesson);
    } else {
      throw new LessonProcessingException(String.format(LESSON_NOT_FOUND_BY_ID_MSG, lessonDto.getId()));
    }
  }

  public void delete(Long id) {
    if (lessonRepository.existsById(id)) {
      lessonRepository.deleteById(id);
    } else {
     throw new LessonProcessingException(String.format(LESSON_NOT_FOUND_BY_ID_MSG, id));
    }
  }

  private Lesson findLessonById(Long id) {
    return lessonRepository.findById(id)
        .orElseThrow(() -> new LessonProcessingException(String.format(LESSON_NOT_FOUND_BY_ID_MSG, id)));
  }

  private void validateLessonDto(LessonDto lessonDto) {
    Set<String> violations = lessonDtoValidator.validate(lessonDto);
    if (!violations.isEmpty()) {
      throw new LessonDtoValidationException(violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }

    if (!lessonDto.getTeacherDto().getSubjectDtoSet().contains(lessonDto.getSubjectDto())) {
      throw new LessonDtoValidationException(TEACHER_HAS_NO_RIGHT_FOR_SUBJECT_MSG);
    }
  }

  private void validateIdIsNotNull(LessonDto lessonDto) {
    if (lessonDto.getId() == null) {
      throw new LessonDtoValidationException(LESSON_ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }

}
