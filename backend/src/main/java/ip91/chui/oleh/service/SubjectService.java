package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.SubjectDtoValidationException;
import ip91.chui.oleh.exception.SubjectProcessingException;
import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.entity.Subject;
import ip91.chui.oleh.model.mapping.SubjectMapper;
import ip91.chui.oleh.repository.SubjectRepository;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

  private static final String SUBJECT_NOT_FOUND_BY_ID = "Subject hasn't been found with ID = (%d)";

  private final SubjectRepository subjectRepository;
  private final SubjectMapper subjectMapper;
  private final DtoValidator<SubjectDto> subjectDtoValidator;

  public List<SubjectDto> getAll() {
    return subjectRepository.findAll()
        .stream()
        .map(subjectMapper::subjectToDto)
        .collect(Collectors.toList());
  }

  public SubjectDto getById(Long id) {
    return subjectMapper.subjectToDto(findSubjectById(id));
  }

  public SubjectDto create(SubjectDto subjectDto) {
    validateSubjectDto(subjectDto);

    Subject subjectToSave = subjectMapper.dtoToSubject(subjectDto);
    Subject savedSubject =  subjectRepository.save(subjectToSave);

    return subjectMapper.subjectToDto(savedSubject);
  }

  public SubjectDto update(SubjectDto subjectDto) {
    validateSubjectDto(subjectDto);

    Subject subjectToUpdate = findSubjectById(subjectDto.getId());
    subjectToUpdate.setName(subjectDto.getName());

    Subject updatedSubject = subjectRepository.save(subjectToUpdate);

    return subjectMapper.subjectToDto(updatedSubject);
  }

  public void delete(Long id) {
    Subject subject = findSubjectById(id);

    subjectRepository.deleteById(subject.getId());
  }

  private Subject findSubjectById(Long id) {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new SubjectProcessingException(String.format(SUBJECT_NOT_FOUND_BY_ID, id)));
  }

  private void validateSubjectDto(SubjectDto subjectDto) {
    Set<String> violations = subjectDtoValidator.validate(subjectDto);
    if (!violations.isEmpty()) {
      throw new SubjectDtoValidationException(violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }
  }

}
