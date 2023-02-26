package ip91.chui.oleh.model;

import ip91.chui.oleh.model.entity.Group;
import ip91.chui.oleh.model.entity.GroupLimits;

public class SimpleFitnessFunction {

  private static final int FAIL_SUBJECT_COUNT_FINE = 100;

  public int process(Group group, GroupLimits groupLimits) {
    return checkByGroupLimits(group, groupLimits);
  }

  private int checkByGroupLimits(Group group, GroupLimits groupLimits) {
    return groupLimits.getGroupMaxSubjectCountMap().entrySet().stream()
        .reduce(0, (acc, entry) -> {
          long count = group.getLessons().stream().filter(lesson -> lesson.getSubject().equals(entry.getKey())).count();
          return acc + FAIL_SUBJECT_COUNT_FINE * (int) Math.abs(entry.getValue() - count);
        }, Integer::sum);
  }

}
