package ip91.chui.oleh.service;

import ip91.chui.oleh.algorithm.EvolutionaryAlgorithm;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.entity.TimeTable;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.TimeTableDtoLightWeightMapper;
import ip91.chui.oleh.repository.TimeTableRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeTableService {

  private final EvolutionaryAlgorithm algorithm;
  private final AuthenticationService authService;
  private final TimeTableRepository timeTableRepository;
  private final TimeTableDtoLightWeightMapper timeTableDtoLightWeightMapper;

  public TimeTableDtoLightWeight generate() {
    return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(algorithm.generate());
  }

  public TimeTableDtoLightWeight create(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    TimeTable timeTableToSave = timeTableDtoLightWeightMapper.toTimeTable(timeTableDtoLightWeight);
    timeTableToSave.setUser(user);
    TimeTable savedTimeTable = timeTableRepository.save(timeTableToSave);

    return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(savedTimeTable);
  }

}
