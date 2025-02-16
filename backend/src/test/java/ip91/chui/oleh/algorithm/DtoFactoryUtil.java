package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.model.dto.GroupDto;
import java.util.concurrent.atomic.AtomicLong;

public final class DtoFactoryUtil {

  public static final Long SUBJECT_ID_START = 10L;

  private DtoFactoryUtil() {}

  public static GroupDto groupWithUniqueSubjectIds(long groupId, int lessonsCount) {
    GroupDto group = DefaultDtoFactoryUtil.group(groupId, lessonsCount);
    AtomicLong uniqueId = new AtomicLong();
    group.getLessons().forEach(lesson -> lesson.getSubjectDto().setId(uniqueId.incrementAndGet()));
    return group;
  }

  public static GroupDto groupWithDuplicatedSubjectIds(
      long groupId, int lessonsCount, int duplicatesCount) {

    GroupDto group = DefaultDtoFactoryUtil.group(groupId, lessonsCount);
    AtomicLong uniqueId = new AtomicLong(SUBJECT_ID_START);
    group.getLessons().stream().skip(duplicatesCount)
        .forEach(lesson -> lesson.getSubjectDto().setId(uniqueId.incrementAndGet()));
    return group;
  }
}
