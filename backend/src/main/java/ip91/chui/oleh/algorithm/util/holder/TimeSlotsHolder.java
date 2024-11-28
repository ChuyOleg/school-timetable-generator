package ip91.chui.oleh.algorithm.util.holder;

import ip91.chui.oleh.exception.TimeSlotProcessingException;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import ip91.chui.oleh.model.mapping.TimeSlotMapper;
import ip91.chui.oleh.repository.TimeSlotRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequestScope
@RequiredArgsConstructor
public class TimeSlotsHolder {

  private static final String TIMESLOT_NOT_FOUND_BY_VALUES = "TimeSlot hasn't been found with values (%s, %s, %s)";

  private final TimeSlotRepository timeSlotRepository;
  private final TimeSlotMapper timeSlotMapper;
  @Getter
  private Set<TimeSlotDto> timeSlots;

  @PostConstruct
  void afterInit() {
    this.timeSlots = timeSlotRepository.findAll()
        .stream()
        .map(timeSlotMapper::timeSlotToDto)
        .collect(Collectors.toSet());
  }

  public TimeSlotDto getTimeSlotByFields(WeekType weekType, DayOfWeek day, int shift, int lessonNumber) {
    return timeSlots
        .stream()
        .filter(timeslot ->
            timeslot.getWeekType().equals(weekType) &&
            timeslot.getDay().equals(day) && timeslot.getShift() == shift &&
            timeslot.getLessonNumber() == lessonNumber
        )
        .findFirst()
        .orElseThrow(() -> new TimeSlotProcessingException(
            String.format(TIMESLOT_NOT_FOUND_BY_VALUES, weekType, day, lessonNumber)
        ));
  }

}
