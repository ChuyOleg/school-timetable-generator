package ip91.chui.oleh.service;

import ip91.chui.oleh.algorithm.Runner;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.dto.TimeTableDto;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.model.mapping.TimeSlotMapper;
import ip91.chui.oleh.model.mapping.TimeTableDtoLightWeightMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.repository.TimeSlotRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeTableService {

  private final AuthenticationService authService;
  private final GroupRepository groupRepository;
  private final TimeSlotRepository timeSlotRepository;
  private final Runner runner;
  private final TimeTableDtoLightWeightMapper timeTableDtoLightWeightMapper;
  private final GroupMapper groupMapper;
  private final TimeSlotMapper timeSlotMapper;

  public TimeTableDtoLightWeight generate() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    Set<GroupDto> groupDtoSet = groupRepository.findAllByUserId(user.getId())
        .stream()
        .map(groupMapper::groupToDto)
        .collect(Collectors.toSet());
    Set<TimeSlotDto> timeSlotDtoSet = timeSlotRepository.findAll()
        .stream()
        .map(timeSlotMapper::timeSlotToDto)
        .collect(Collectors.toSet());

    TimeTableDto timeTableDto = runner.run(groupDtoSet, timeSlotDtoSet);
    return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(timeTableDto);
  }

}
