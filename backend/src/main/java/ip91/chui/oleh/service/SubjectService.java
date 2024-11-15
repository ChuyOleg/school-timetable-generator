package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.SubjectDtoValidationException;
import ip91.chui.oleh.exception.SubjectProcessingException;
import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.entity.Subject;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.SubjectMapper;
import ip91.chui.oleh.repository.SubjectRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

  private static final String SUBJECT_NOT_FOUND_BY_ID_MSG = "Subject hasn't been found with ID = (%d)";
  private static final String SUBJECT_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the subject";

  private final AuthenticationService authService;
  private final SubjectRepository subjectRepository;
  private final SubjectMapper subjectMapper;
  private final DtoValidator<SubjectDto> subjectDtoValidator;

  public List<SubjectDto> getAll() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return subjectRepository.findAllByUserId(user.getId())
        .stream()
        .map(subjectMapper::subjectToDto)
        .collect(Collectors.toList());
  }

  public SubjectDto getById(Long id) {
    return subjectMapper.subjectToDto(findSubjectById(id));
  }

  public SubjectDto create(SubjectDto subjectDto) {
    validateSubjectDto(subjectDto);

    User user = authService.extractPrincipalFromSecurityContextHolder();

    Subject subjectToSave = subjectMapper.dtoToSubject(subjectDto);
    subjectToSave.setUser(user);
    Subject savedSubject = subjectRepository.save(subjectToSave);

    return subjectMapper.subjectToDto(savedSubject);
  }

  public SubjectDto update(SubjectDto subjectDto) {
    validateIdIsNotNull(subjectDto);
    validateSubjectDto(subjectDto);

    if (subjectRepository.existsById(subjectDto.getId())) {
      Subject subjectToUpdate = subjectMapper.dtoToSubject(subjectDto);
      Subject updatedSubject = subjectRepository.save(subjectToUpdate);
      return subjectMapper.subjectToDto(updatedSubject);
    } else {
      throw new SubjectProcessingException(String.format(SUBJECT_NOT_FOUND_BY_ID_MSG, subjectDto.getId()));
    }
  }

  public void delete(Long id) {
    if (subjectRepository.existsById(id)) {
      subjectRepository.deleteById(id);
    } else {
      throw new SubjectProcessingException(String.format(SUBJECT_NOT_FOUND_BY_ID_MSG, id));
    }
  }

  private Subject findSubjectById(Long id) {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new SubjectProcessingException(String.format(SUBJECT_NOT_FOUND_BY_ID_MSG, id)));
  }

  private void validateSubjectDto(SubjectDto subjectDto) {
    Set<String> violations = subjectDtoValidator.validate(subjectDto);
    if (!violations.isEmpty()) {
      throw new SubjectDtoValidationException(
          violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }
  }

  private void validateIdIsNotNull(SubjectDto subjectDto) {
    if (subjectDto.getId() == null) {
      throw new SubjectDtoValidationException(SUBJECT_ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }

}
