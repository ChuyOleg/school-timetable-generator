package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.GroupDtoValidationException;
import ip91.chui.oleh.exception.GroupProcessingException;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.Group;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

  private static final String GROUP_NOT_FOUND_BY_ID_MSG = "Group hasn't been found with ID = (%d)";
  private static final String GROUP_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the group";

  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;
  private final DtoValidator<GroupDto> groupDtoDtoValidator;

  public List<GroupDto> getAll() {
    return groupRepository.findAll()
        .stream()
        .map(groupMapper::groupToDtoLimitedInfo)
        .collect(Collectors.toList());
  }

  public GroupDto getById(Long id) {
    return groupMapper.groupToDto(findGroupById(id));
  }

  @Transactional
  public GroupDto create(GroupDto groupDto) {
    validateGroupDto(groupDto);

    Group groupToSave = groupMapper.dtoToGroup(groupDto);
    Group savedGroup = groupRepository.save(groupToSave);

    return groupMapper.groupToDto(savedGroup);
  }

  @Transactional
  public GroupDto update(GroupDto groupDto) {
    validateIdIsNotNull(groupDto);
    validateGroupDto(groupDto);

    if (groupRepository.existsById(groupDto.getId())) {
      Group groupToUpdate = groupMapper.dtoToGroup(groupDto);
      Group updatedGroup = groupRepository.save(groupToUpdate);
      return groupMapper.groupToDto(updatedGroup);
    } else {
      throw new GroupProcessingException(String.format(GROUP_NOT_FOUND_BY_ID_MSG, groupDto.getId()));
    }
  }

  public void delete(Long id) {
    if (groupRepository.existsById(id)) {
      groupRepository.deleteById(id);
    } else {
      throw new GroupProcessingException(String.format(GROUP_NOT_FOUND_BY_ID_MSG, id));
    }
  }

  private Group findGroupById(Long id) {
    return groupRepository.findById(id)
        .orElseThrow(() -> new GroupProcessingException(String.format(GROUP_NOT_FOUND_BY_ID_MSG, id)));
  }

  private void validateGroupDto(GroupDto groupDto) {
    Set<String> violations = groupDtoDtoValidator.validate(groupDto);
    if (!violations.isEmpty()) {
      throw new GroupDtoValidationException(violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }
  }

  private void validateIdIsNotNull(GroupDto groupDto) {
    if (groupDto.getId() == null) {
      throw new GroupDtoValidationException(GROUP_ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }

}
