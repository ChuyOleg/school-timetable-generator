package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.ShiftsIntersectionDtoValidationException;
import ip91.chui.oleh.model.dto.ShiftsIntersectionDto;
import ip91.chui.oleh.model.entity.ShiftsIntersection;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.ShiftsIntersectionMapper;
import ip91.chui.oleh.repository.ShiftsIntersectionRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import ip91.chui.oleh.validator.DtoValidator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShiftsIntersectionService {

  private static final String NOT_FOUND_BY_ID_MSG = "ShiftsIntersection hasn't been found with ID = (%d)";
  private static final String ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the shiftsIntersection";

  private final AuthenticationService authService;
  private final ShiftsIntersectionRepository repository;
  private final ShiftsIntersectionMapper mapper;
  private final DtoValidator<ShiftsIntersectionDto> dtoValidator;

  public List<ShiftsIntersectionDto> getAll() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return repository.findAllByUserId(user.getId())
        .stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  public ShiftsIntersectionDto create(ShiftsIntersectionDto dto) {
    validateDto(dto);

    User user = authService.extractPrincipalFromSecurityContextHolder();

    ShiftsIntersection entityToSave = mapper.toEntity(dto);
    entityToSave.setUser(user);
    ShiftsIntersection savedEntity = repository.save(entityToSave);

    return mapper.toDto(savedEntity);
  }

  public ShiftsIntersectionDto update(ShiftsIntersectionDto dto) {
    validateIdIsNotNull(dto);
    validateDto(dto);

    if (!repository.existsById(dto.getId())) {
      throw new ShiftsIntersectionProcessingException(String.format(NOT_FOUND_BY_ID_MSG, dto.getId()));
    }

    ShiftsIntersection entityToUpdate = mapper.toEntity(dto);
    ShiftsIntersection updatedEntity = repository.save(entityToUpdate);
    return mapper.toDto(updatedEntity);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ShiftsIntersectionProcessingException(String.format(NOT_FOUND_BY_ID_MSG, id));
    }

    repository.deleteById(id);
  }

  private void validateDto(ShiftsIntersectionDto dto) {
    Set<String> violations = dtoValidator.validate(dto);
    if (!violations.isEmpty()) {
      throw new ShiftsIntersectionDtoValidationException(
          violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }
  }

  private void validateIdIsNotNull(ShiftsIntersectionDto dto) {
    if (Objects.isNull(dto.getId())) {
      throw new ShiftsIntersectionDtoValidationException(ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }
}
