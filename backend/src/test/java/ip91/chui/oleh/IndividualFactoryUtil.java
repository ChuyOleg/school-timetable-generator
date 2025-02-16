package ip91.chui.oleh;

import static ip91.chui.oleh.algorithm.DtoFactoryUtil.*;

import ip91.chui.oleh.algorithm.model.Individual;
import java.util.stream.LongStream;

public final class IndividualFactoryUtil {

  private IndividualFactoryUtil() {}

  public static Individual individualWithUniqueSubjectIdsPerGroup(int groupsCount, int lessonsCount) {
    Object[] groups = LongStream.rangeClosed(1, groupsCount).boxed()
        .map(groupId -> groupWithUniqueSubjectIds(groupId, lessonsCount))
        .toArray();

    return new Individual(groups);
  }

  public static Individual individualWithDuplicatedSubjectIdsPerGroup(int groupsCount,
      int lessonsCount, int duplicatesCount) {

    Object[] groups = LongStream.rangeClosed(1, groupsCount).boxed()
        .map(groupId -> groupWithDuplicatedSubjectIds(groupId, lessonsCount, duplicatesCount))
        .toArray();

    return new Individual(groups);
  }
}
