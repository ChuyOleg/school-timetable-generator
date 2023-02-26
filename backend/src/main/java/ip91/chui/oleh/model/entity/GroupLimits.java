package ip91.chui.oleh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupLimits {

  private final Map<Subject, Double> groupMaxSubjectCountMap;
  private TimeSlot interschoolCombine;

}
