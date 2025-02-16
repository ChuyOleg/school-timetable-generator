package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelUtil {

  private static final Long DEFAULT_TEACHER_ID = 1L;
  private static final String DEFAULT_TEACHER_NAME = "dummyTeacherName";
  private static final int DEFAULT_TEACHER_MAX_HOURS = 18;

  private static final Long DEFAULT_ROOM_ID = 1L;
  private static final String DEFAULT_ROOM_NAME = "dummyRoomName";
  private static final int DEFAULT_ROOM_CAPACITY = 2;

  private static final Long DEFAULT_SUBJECT_ID = 1L;
  private static final String DEFAULT_SUBJECT_NAME = "dummySubjectName";

  private static final Long DEFAULT_LESSON_ID = 1L;

  private static final Long DEFAULT_TIMESLOT_ID = 1L;
  private static final int DEFAULT_TIMESLOT_LESSON_NUM = 1;
  private static final DayOfWeek DEFAULT_TIMESLOT_DAY_OF_WEEK = DayOfWeek.MONDAY;
  private static final WeekType DEFAULT_TIMESLOT_WEEK_TYPE = WeekType.BOTH;

  public Individual createPrimitiveIndividual(int groupsNumber, int gradeNumber, int shift) {
    List<GroupDto> groups = createPrimitiveGroups(groupsNumber, gradeNumber, shift);
    return new Individual(groups.toArray());
  }

//  public GroupDto createGroupWithUniqueSubjects() {
//    GroupDto groupDto = createPrimitiveGroup(1L, 8, "A", 1);
//
//  }

  public Individual createPrimitiveIndividualWithDefaultLessons(
      int groupsCount, int gradeNumber, int shift, int lessonsCount) {

    List<GroupDto> groups = createPrimitiveGroups(groupsCount, gradeNumber, shift);
//    Set<LessonDto> lessons = defaultLessons(lessonsCount);

//    groups.forEach(group -> linkGroupWithLessons(group, lessons));
    return new Individual(groups.toArray());
  }

  public void linkGroupWithLessons(GroupDto groupDto, Set<LessonDto> lessons) {
    groupDto.setLessons(lessons);
    lessons.forEach(lesson -> lesson.setGroupDto(groupDto));
  }

  public List<GroupDto> createPrimitiveGroups(int groupsCount, int gradeNumber, int shift) {
    return LongStream.range(0, groupsCount).boxed()
        .map(groupId -> createPrimitiveGroup(groupId, gradeNumber, "A", shift))
        .collect(Collectors.toList());
  }

  public GroupDto createPrimitiveGroup(Long id, int gradeNumber, String letter, int shift) {
    return GroupDto.builder()
        .id(id).gradeNumber(gradeNumber).letter(letter).shift(shift)
        .build();
  }

//  private Set<LessonDto> uniqueLessons(int lessonsCount) {
//    return LongStream.range(0, lessonsCount).boxed()
////        .map(id -> createUni)
//        .collect(Collectors.toSet());
//  }


  private TeacherDto teacher(Long id) {
    return new TeacherDto(id, DEFAULT_TEACHER_NAME, null, DEFAULT_TEACHER_MAX_HOURS, null);
  }

//  private LessonDto uniqueLesson(Long id) {
//    return new LessonDto(id, null, )
//  }

  private SubjectDto getSubjectByLessonNumber(int lessonNumber) {
    return switch (lessonNumber) {
      case 1 -> new SubjectDto(1L, "Subj 1");
      case 2 -> new SubjectDto(2L, "Subj 2");
      case 3 -> new SubjectDto(3L, "Subj 3");
      case 4 -> new SubjectDto(4L, "Subj 4");
      case 5 -> new SubjectDto(5L, "Subj 5");
      case 6 -> new SubjectDto(6L, "Subj 6");
      case 7 -> new SubjectDto(7L, "Subj 7");
      default -> throw new RuntimeException();
    };
  }

  private TimeSlotDto getMondayTimeSlotByLessonNumber(int lessonNumber) {
    return switch (lessonNumber) {
      case 1 -> new TimeSlotDto(1L, 1, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case 2 -> new TimeSlotDto(2L, 2, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case 3 -> new TimeSlotDto(3L, 3, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case 4 -> new TimeSlotDto(4L, 4, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case 5 -> new TimeSlotDto(5L, 5, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case 6 -> new TimeSlotDto(6L, 6, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case 7 -> new TimeSlotDto(7L, 7, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      default -> throw new RuntimeException();
    };
  }

}
