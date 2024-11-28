package ip91.chui.oleh.service;

import ip91.chui.oleh.algorithm.Algorithm;
import ip91.chui.oleh.algorithm.util.TimetableFinesInformer;
import ip91.chui.oleh.exception.TimeTableDtoLightWeightValidationException;
import ip91.chui.oleh.exception.TimeTableProcessingException;
import ip91.chui.oleh.model.dto.TimeTableFinesDto;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.entity.*;
import ip91.chui.oleh.model.mapping.TimeTableDtoLightWeightMapper;
import ip91.chui.oleh.repository.LessonRepository;
import ip91.chui.oleh.repository.TimeSlotRepository;
import ip91.chui.oleh.repository.TimeTableRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TimeTableService {

  private static final String TIMETABLE_NOT_FOUND_MSG = "Timetable hasn't been found for current user";
  private static final String TIMETABLE_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the timetable";

  private final Algorithm algorithm;
  private final TimetableFinesInformer finesInformer;
  private final AuthenticationService authService;
  private final TimeTableRepository timeTableRepository;
  private final LessonRepository lessonRepository;
  private final TimeSlotRepository timeSlotRepository;
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

  public TimeTableFinesDto checkFitness(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    return finesInformer.check(timeTableDtoLightWeight);
  }

  @Transactional
  public TimeTableDtoLightWeight create(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    TimeTable timeTableToSave = timeTableDtoLightWeightMapper.toTimeTable(timeTableDtoLightWeight);
    timeTableToSave.setUser(user);
    setTimeSlotsToLessons(timeTableToSave.getLessons());
    TimeTable savedTimeTable = timeTableRepository.save(timeTableToSave);

    return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(savedTimeTable);
  }

  public TimeTableDtoLightWeight update(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    validateIdIsNotNull(timeTableDtoLightWeight);

    if (timeTableRepository.existsById(timeTableDtoLightWeight.getId())) {
      TimeTable timeTableToUpdate = timeTableDtoLightWeightMapper.toTimeTable(timeTableDtoLightWeight);
      User user = authService.extractPrincipalFromSecurityContextHolder();
      timeTableToUpdate.setUser(user);

      TimeTable updatedTimeTable = timeTableRepository.save(timeTableToUpdate);
      return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(updatedTimeTable);
    } else {
      throw new TimeTableProcessingException(TIMETABLE_NOT_FOUND_MSG);
    }
  }

  @Transactional
  public void delete(Long id) {
    if (timeTableRepository.existsById(id)) {
      lessonRepository.deleteAllByTimeTableId(id);
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

  private void setTimeSlotsToLessons(Set<Lesson> lessons) {
    Map<Long, TimeSlot> timeSlotById = new HashMap<>();
    List<TimeSlot> timeSlots = timeSlotRepository.findAll();

    timeSlots.forEach(timeSlot -> timeSlotById.put(timeSlot.getId(), timeSlot));

    lessons.forEach(lesson -> lesson.setTimeSlot(timeSlotById.get(lesson.getTimeSlot().getId())));
  }

}
