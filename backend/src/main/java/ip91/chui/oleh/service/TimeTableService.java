package ip91.chui.oleh.service;

import ip91.chui.oleh.algorithm.EvolutionaryAlgorithm;
import ip91.chui.oleh.exception.TimeTableDtoLightWeightValidationException;
import ip91.chui.oleh.exception.TimeTableProcessingException;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.entity.TimeTable;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.TimeTableDtoLightWeightMapper;
import ip91.chui.oleh.repository.TimeTableRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeTableService {

  private static final String TIMETABLE_NOT_FOUND_MSG = "Timetable hasn't been found for current user";
  private static final String TIMETABLE_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the timetable";

  private final EvolutionaryAlgorithm algorithm;
  private final AuthenticationService authService;
  private final TimeTableRepository timeTableRepository;
  private final TimeTableDtoLightWeightMapper timeTableDtoLightWeightMapper;

  public TimeTableDtoLightWeight getForUser() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    Optional<TimeTable> optionalTimeTable = timeTableRepository.findByUserId(user.getId());

    return optionalTimeTable
        .map(timeTableDtoLightWeightMapper::toTimeTableDtoLightWeight)
        .orElse(null);
  }

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

  public TimeTableDtoLightWeight update(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    validateIdIsNotNull(timeTableDtoLightWeight);

    if (timeTableRepository.existsById(timeTableDtoLightWeight.getId())) {
      TimeTable timeTableToUpdate = timeTableDtoLightWeightMapper.toTimeTable(timeTableDtoLightWeight);
      TimeTable updatedTimeTable = timeTableRepository.save(timeTableToUpdate);
      return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(updatedTimeTable);
    } else {
      throw new TimeTableProcessingException(TIMETABLE_NOT_FOUND_MSG);
    }
  }

  public void delete(Long id) {
    if (timeTableRepository.existsById(id)) {
      timeTableRepository.deleteById(id);
    } else {
      throw new TimeTableProcessingException(TIMETABLE_NOT_FOUND_MSG);
    }
  }

  private void validateIdIsNotNull(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    if (timeTableDtoLightWeight.getId() == null) {
      throw new TimeTableDtoLightWeightValidationException(TIMETABLE_ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }

}
