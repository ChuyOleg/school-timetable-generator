package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.GroupDtoValidationException;
import ip91.chui.oleh.exception.GroupProcessingException;
import ip91.chui.oleh.exception.TimeSlotProcessingException;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.*;
import ip91.chui.oleh.model.entity.teacher.Teacher;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.repository.teacher.TeacherRepository;
import ip91.chui.oleh.repository.TimeSlotRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

  private static final String GROUP_NOT_FOUND_BY_ID_MSG = "Group hasn't been found with ID = (%d)";
  private static final String GROUP_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the group";
  private static final String TIMESLOT_NOT_FOUND_BY_VALUES = "TimeSlot hasn't been found with values (%s, %s, %s)";

  private final AuthenticationService authService;
  private final GroupRepository groupRepository;
  private final TimeSlotRepository timeSlotRepository;
  private final TeacherRepository teacherRepository;
  private final GroupMapper groupMapper;
  private final DtoValidator<GroupDto> groupDtoDtoValidator;

  public List<GroupDto> getAll() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return groupRepository.findAllByUserId(user.getId())
        .stream()
        .map(groupMapper::groupToDtoLimitedInfo)
        .collect(Collectors.toList());
  }

  public GroupDto getById(Long id) {
    Group group = findGroupById(id);
    User user = authService.extractPrincipalFromSecurityContextHolder();
    if (group.getGroupLimits() != null) {
      setTeacherToSubjectLimits(group.getGroupLimits().getSubjectLimitsSet(), user.getId());
    }
    return groupMapper.groupToDto(group);
  }

  @Transactional
  public GroupDto create(GroupDto groupDto) {
    validateGroupDto(groupDto);

    User user = authService.extractPrincipalFromSecurityContextHolder();

    Group groupToSave = groupMapper.dtoToGroup(groupDto);

    groupToSave.setUser(user);
    setTimeSlotIdIfItIsNull(groupToSave);
    if (groupToSave.getGroupLimits() != null) {
      setTeacherToSubjectLimits(groupToSave.getGroupLimits().getSubjectLimitsSet(), user.getId());
    }

    Group savedGroup = groupRepository.save(groupToSave);

    return groupMapper.groupToDto(savedGroup);
  }

  @Transactional
  public GroupDto update(GroupDto groupDto) {
    validateIdIsNotNull(groupDto);
    validateGroupDto(groupDto);

    if (groupRepository.existsById(groupDto.getId())) {
      Group groupToUpdate = groupMapper.dtoToGroup(groupDto);
      setTimeSlotIdIfItIsNull(groupToUpdate);
      User user = authService.extractPrincipalFromSecurityContextHolder();
      if (groupToUpdate.getGroupLimits() != null) {
        setTeacherToSubjectLimits(groupToUpdate.getGroupLimits().getSubjectLimitsSet(), user.getId());
      }

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

  private void setTimeSlotIdIfItIsNull(Group group) {
    if (group.getGroupLimits() == null || group.getGroupLimits().getInterschoolCombine() == null) {
      return;
    }

    TimeSlot combine = group.getGroupLimits().getInterschoolCombine();
    if (combine.getId() == null) {
      TimeSlot timeSlotInDB = timeSlotRepository
          .findTimeSlotByWeekTypeAndDayAndShiftAndLessonNumber(combine.getWeekType(), combine.getDay(), combine.getShift(), combine.getLessonNumber())
          .orElseThrow(() -> new TimeSlotProcessingException(
              String.format(TIMESLOT_NOT_FOUND_BY_VALUES, combine.getWeekType(), combine.getDay(), combine.getLessonNumber())
          ));
      combine.setId(timeSlotInDB.getId());
    }
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

  private void setTeacherToSubjectLimits(Set<SubjectLimits> subjectLimits, Long userId) {
    Map<Long, Teacher> teacherById = new HashMap<>();
    List<Teacher> teachers = teacherRepository.findAllByUserId(userId);

    teachers.forEach(teacher -> teacherById.put(teacher.getId(), teacher));

    subjectLimits.forEach(subjLimits -> {
      subjLimits.setTeacher(teacherById.get(subjLimits.getTeacher().getId()));
      if (subjLimits.getTeacher2() != null) {
        subjLimits.setTeacher2(teacherById.get(subjLimits.getTeacher2().getId()));
      }
    });
  }

}
