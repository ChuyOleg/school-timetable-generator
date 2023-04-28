package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.dto.TimeTableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class Runner {

  private final SimpleScheduleGenerator generator;

  public TimeTableDto run(Set<GroupDto> groupDtoSet, Set<TimeSlotDto> timeSlotDtoSet) {
    return generator.generate(groupDtoSet, timeSlotDtoSet);
  }

}
