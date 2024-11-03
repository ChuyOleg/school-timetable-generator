package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.RoomDto;
import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import java.time.DayOfWeek;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultDtoFactoryUtil {

  public final Long DEFAULT_GROUP_ID = 1L;
  public final int DEFAULT_GROUP_GRADE_NUMBER = 8;
  public final String DEFAULT_GROUP_LETTER = "A";
  public final int DEFAULT_GROUP_SHIFT = 1;

  public final Long DEFAULT_TEACHER_ID = 1L;
  public final String DEFAULT_TEACHER_NAME = "dummyTeacherName";
  public final int DEFAULT_TEACHER_MAX_HOURS = 18;

  public final Long DEFAULT_ROOM_ID = 1L;
  public final String DEFAULT_ROOM_NAME = "dummyRoomName";
  public final int DEFAULT_ROOM_CAPACITY = 2;

  public final Long DEFAULT_SUBJECT_ID = 1L;
  public final String DEFAULT_SUBJECT_NAME = "dummySubjectName";

  public final Long DEFAULT_LESSON_ID = 1L;

  public final Long DEFAULT_TIMESLOT_ID = 1L;
  public final int DEFAULT_TIMESLOT_LESSON_NUM = 1;
  public final DayOfWeek DEFAULT_TIMESLOT_DAY_OF_WEEK = DayOfWeek.MONDAY;
  public final WeekType DEFAULT_TIMESLOT_WEEK_TYPE = WeekType.BOTH;

  public GroupDto group(int lessonsCount) {
    GroupDto group = primitiveGroup();
    Set<LessonDto> lessons = lessonsWithUniqueIds(lessonsCount);
    ModelUtil.linkGroupWithLessons(group, lessons);
    return group;
  }

  public GroupDto group(long id, int lessonsCount) {
    GroupDto group = group(lessonsCount);
    group.setId(id);
    return group;
  }

  private GroupDto primitiveGroup() {
    return GroupDto.builder()
        .id(DEFAULT_GROUP_ID).gradeNumber(DEFAULT_GROUP_GRADE_NUMBER)
        .letter(DEFAULT_GROUP_LETTER).shift(DEFAULT_GROUP_SHIFT)
        .build();
  }

  private Set<LessonDto> lessonsWithUniqueIds(int lessonsCount) {
    return LongStream.range(0, lessonsCount).boxed()
        .map(DefaultDtoFactoryUtil::lesson)
        .collect(Collectors.toSet());
  }

  public LessonDto lesson() {
    return new LessonDto(DEFAULT_LESSON_ID, null, teacher(), subject(), room(), timeslot());
  }

  public LessonDto lesson(long id) {
    LessonDto lesson = lesson();
    lesson.setId(id);
    return lesson;
  }

  public LessonDto lesson(long id, long subjectId, long teacherId) {
    LessonDto lesson = lesson(id);
    lesson.getSubjectDto().setId(subjectId);
    lesson.getTeacherDto().setId(teacherId);
    return lesson;
  }

  public Set<LessonDto> subGroupsLesson(long id1, long id2, long subjectId,
      long teacher1Id, long teacher2Id) {

    return Set.of(lesson(id1, subjectId, teacher1Id), lesson(id2, subjectId, teacher2Id));
  }

  public TeacherDto teacher() {
    return new TeacherDto(DEFAULT_TEACHER_ID, DEFAULT_TEACHER_NAME, null, DEFAULT_TEACHER_MAX_HOURS);
  }

  public RoomDto room() {
    return new RoomDto(DEFAULT_ROOM_ID, DEFAULT_ROOM_NAME, DEFAULT_ROOM_CAPACITY);
  }

  public SubjectDto subject() {
    return new SubjectDto(DEFAULT_SUBJECT_ID, DEFAULT_SUBJECT_NAME);
  }

  private TimeSlotDto timeslot() {
    return new TimeSlotDto(DEFAULT_TIMESLOT_ID, DEFAULT_TIMESLOT_LESSON_NUM,
        DEFAULT_TIMESLOT_DAY_OF_WEEK, DEFAULT_TIMESLOT_WEEK_TYPE);
  }
}
