package ip91.chui.oleh.algorithm.util;

import ip91.chui.oleh.algorithm.util.holder.GroupsHolder;
import ip91.chui.oleh.algorithm.util.holder.RoomsHolder;
import ip91.chui.oleh.algorithm.util.holder.TimeSlotsHolder;
import ip91.chui.oleh.model.dto.*;
import ip91.chui.oleh.model.dto.lightweigth.LessonDtoLightWeight;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableFinesInformerTest {

  private static long nextLessonId = 1L;
  private static long nextTimetableId = 1L;

  private static final long GROUP_ID_1 = 1L;
  private static final long GROUP_ID_2 = 2L;
  private static final long GROUP_ID_3 = 3L;

  private static final long TIME_SLOT_ID_1 = 1L;
  private static final long TIME_SLOT_ID_2 = 2L;
  private static final long TIME_SLOT_ID_3 = 3L;
  private static final long TIME_SLOT_ID_4 = 4L;
  private static final long TIME_SLOT_ID_5 = 5L;
  private static final long TIME_SLOT_ID_6 = 6L;
  private static final long TIME_SLOT_ID_7 = 7L;

  private static final long ROOM_ID_1 = 1L;

  private static final long TEACHER_ID_1 = 1L;

  private static final long SUBJECT_ID_1 = 1L;
  private static final long SUBJECT_ID_2 = 2L;
  private static final long SUBJECT_ID_3 = 3L;
  private static final long SUBJECT_ID_4 = 4L;
  private static final long SUBJECT_ID_5 = 5L;
  private static final long SUBJECT_ID_6 = 6L;
  private static final long SUBJECT_ID_7 = 7L;

  @Mock
  private GroupsHolder groupsHolder;
  @Mock
  private TimeSlotsHolder timeSlotsHolder;
  @Mock
  private RoomsHolder roomsHolder;
  @InjectMocks
  private TimetableFinesInformer timetableFinesInformer;

  @BeforeEach
  void init() {
    when(groupsHolder.getGroups()).thenReturn(mockGroupsHolderBehavior());
    when(timeSlotsHolder.getTimeSlots()).thenReturn(mockTimeSlotsHolderBehavior());
    when(roomsHolder.getRooms()).thenReturn(mockRoomsHolderBehavior());
  }

  @Test
  void Should_ReturnNoFines_WhenTimeTableIsValid() {
    TimeTableDtoLightWeight timetable = createTimetableWithoutViolations();

    TimeTableFinesDto expectedResult = new TimeTableFinesDto(new HashSet<>(), new HashSet<>(), new HashSet<>());

    TimeTableFinesDto actualResult = timetableFinesInformer.check(timetable);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void Should_ReturnSubjectFines_WhenSubjectPerDayViolations() {
    TimeTableDtoLightWeight timetable = createTimetableWithSubjectPerDayViolation();

    Set<LessonDto> subjectFines = timetable.getLessons().stream()
        .filter(lesson ->
            lesson.getTimeSlotId() == TIME_SLOT_ID_1 ||
            lesson.getTimeSlotId() == TIME_SLOT_ID_3 ||
            lesson.getTimeSlotId() == TIME_SLOT_ID_6)
        .map(lesson -> new LessonDto(
            lesson.getId(), getGroupById(lesson.getGroupId()), getDefaultTeacherDto(),
            getSubjectById(lesson.getSubjectId()), getDefaultRoomDtoWithCapacity2(), getTimeSlotById((lesson.getTimeSlotId()))
        ))
        .collect(Collectors.toSet());

    TimeTableFinesDto expectedResult = new TimeTableFinesDto(subjectFines, new HashSet<>(), new HashSet<>());

    TimeTableFinesDto actualResult = timetableFinesInformer.check(timetable);

    assertEquals(expectedResult.getSubjectFines().size(), actualResult.getSubjectFines().size());
    assertEquals(expectedResult.getTeacherFines().size(), actualResult.getTeacherFines().size());
    assertEquals(expectedResult.getRoomFines().size(), actualResult.getRoomFines().size());
  }

  private TimeTableDtoLightWeight createTimetableWithSubjectPerDayViolation() {
    Set<LessonDtoLightWeight> lessons = createLessonsWithSubjectPerDayViolation();

    return new TimeTableDtoLightWeight(nextTimetableId++, lessons);
  }

  private TimeTableDtoLightWeight createTimetableWithoutViolations() {
    Set<LessonDtoLightWeight> lessons = createLessonsWithoutViolations();

    return new TimeTableDtoLightWeight(nextTimetableId++, lessons);
  }

  private Set<LessonDtoLightWeight> createLessonsWithSubjectPerDayViolation() {
    Set<LessonDtoLightWeight> lessons = createLessonsWithoutViolations();

    lessons.forEach(lesson -> {
      if (lesson.getTimeSlotId() == TIME_SLOT_ID_1 ||
          lesson.getTimeSlotId() == TIME_SLOT_ID_3 ||
          lesson.getTimeSlotId() == TIME_SLOT_ID_6) {
        lesson.setSubjectId(SUBJECT_ID_3);
      }
    });

    return lessons;
  }


  private Set<LessonDtoLightWeight> createLessonsWithoutViolations() {
    Set<LessonDtoLightWeight> lessons = new HashSet<>();

    for (int lessonNum = 1; lessonNum < 8; lessonNum++) {
      LessonDtoLightWeight lesson = new LessonDtoLightWeight(
          nextLessonId++, GROUP_ID_1, getDefaultTeacherDto().getId(), getSubjectById(lessonNum).getId(),
          getDefaultRoomDtoWithCapacity2().getId(), getTimeSlotById(lessonNum).getId()
      );
      lessons.add(lesson);
    }

    return lessons;
  }

  private GroupDto createEmptyGroup(long id, int gradeNumber) {
    return GroupDto.builder()
        .id(id)
        .gradeNumber(gradeNumber)
        .shift(1)
        .build();
  }

  private TeacherDto getDefaultTeacherDto() {
    return new TeacherDto(TEACHER_ID_1, "Default", null, 18, null);
  }

  private RoomDto getDefaultRoomDtoWithCapacity2() {
    return new RoomDto(ROOM_ID_1, "Default", 2, null);
  }

  private Set<GroupDto> mockGroupsHolderBehavior() {
    Set<GroupDto> groups = new HashSet<>();

    groups.add(getGroupById(GROUP_ID_1));
    groups.add(getGroupById(GROUP_ID_2));
    groups.add(getGroupById(GROUP_ID_3));

    return groups;
  }

  private GroupDto getGroupById(long groupId) {
    return switch ((int) groupId) {
      case (int) GROUP_ID_1 -> createEmptyGroup(GROUP_ID_1, 5);
      case (int) GROUP_ID_2 -> createEmptyGroup(GROUP_ID_2, 6);
      case (int) GROUP_ID_3 -> createEmptyGroup(GROUP_ID_3, 7);
      default -> throw new RuntimeException();
    };
  }

  private Set<TimeSlotDto> mockTimeSlotsHolderBehavior() {
    Set<TimeSlotDto> timeSlots = new HashSet<>();

    IntStream.range(1, 8).forEach(lessonNum -> timeSlots.add(getTimeSlotById(lessonNum)));

    return timeSlots;
  }

  private Set<RoomDto> mockRoomsHolderBehavior() {
    Set<RoomDto> rooms = new HashSet<>();

    rooms.add(getDefaultRoomDtoWithCapacity2());

    return rooms;
  }

  private SubjectDto getSubjectById(long subjectId) {
    return switch ((int) subjectId) {
      case (int) SUBJECT_ID_1 -> new SubjectDto(SUBJECT_ID_1, "Subj 1");
      case (int) SUBJECT_ID_2 -> new SubjectDto(SUBJECT_ID_2, "Subj 2");
      case (int) SUBJECT_ID_3 -> new SubjectDto(SUBJECT_ID_3, "Subj 3");
      case (int) SUBJECT_ID_4 -> new SubjectDto(SUBJECT_ID_4, "Subj 4");
      case (int) SUBJECT_ID_5 -> new SubjectDto(SUBJECT_ID_5, "Subj 5");
      case (int) SUBJECT_ID_6 -> new SubjectDto(SUBJECT_ID_6, "Subj 6");
      case (int) SUBJECT_ID_7 -> new SubjectDto(SUBJECT_ID_7, "Subj 7");
      default -> throw new RuntimeException();
    };
  }

  private TimeSlotDto getTimeSlotById(long timeSlotId) {
    return switch ((int) timeSlotId) {
      case (int) TIME_SLOT_ID_1 -> new TimeSlotDto(TIME_SLOT_ID_1, 1, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case (int) TIME_SLOT_ID_2 -> new TimeSlotDto(TIME_SLOT_ID_2, 2, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case (int) TIME_SLOT_ID_3 -> new TimeSlotDto(TIME_SLOT_ID_3, 3, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case (int) TIME_SLOT_ID_4 -> new TimeSlotDto(TIME_SLOT_ID_4, 4, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case (int) TIME_SLOT_ID_5 -> new TimeSlotDto(TIME_SLOT_ID_5, 5, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case (int) TIME_SLOT_ID_6 -> new TimeSlotDto(TIME_SLOT_ID_6, 6, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      case (int) TIME_SLOT_ID_7 -> new TimeSlotDto(TIME_SLOT_ID_7, 7, DayOfWeek.MONDAY, 1, WeekType.BOTH);
      default -> throw new RuntimeException();
    };
  }

}
