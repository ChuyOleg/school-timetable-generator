package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.*;
import ip91.chui.oleh.model.enumeration.WeekType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TimetableFitnessFunctionTest {

  private static long nextLessonId = 1L;

  private TimetableFitnessFunction timetableFitnessFunction;

  @BeforeEach
  void init() {
    timetableFitnessFunction = new TimetableFitnessFunction();
  }

  @Test
  void Should_ReturnValidValue_WhenNoViolations() {
    final int expectedFitnessScore = 0;

    Individual individual = createTimetableWithoutViolations();

    final int actualFitnessScore = timetableFitnessFunction.calculate(individual);

    assertEquals(expectedFitnessScore, actualFitnessScore);
  }

  @Test
  void Should_ReturnValidValue_WhenViolateSubjectPerDayLimit() {
    final int expectedFitnessScore = 3;

    Individual individual = createTimetableWithSubjectPerDayViolation();

    final int actualFitnessScore = timetableFitnessFunction.calculate(individual);

    assertEquals(expectedFitnessScore, actualFitnessScore);
  }

  @Test
  void Should_ReturnValidValue_WhenViolateTeacherTimeSlot() {
    final int expectedFitnessScore = 2;

    Individual individual = createTimetableWithTeacherTimeslotViolation();

    final int actualFitnessScore = timetableFitnessFunction.calculate(individual);

    assertEquals(expectedFitnessScore, actualFitnessScore);
  }

  @Test
  void Should_ReturnValidValue_WhenViolateRoomTimeSlot() {
    final int expectedFitnessScore = 2;

    Individual individual = createTimetableWithRoomTimeslotViolation();

    final int actualFitnessScore = timetableFitnessFunction.calculate(individual);

    assertEquals(expectedFitnessScore, actualFitnessScore);
  }

  private Individual createTimetableWithSubjectPerDayViolation() {
    List<GroupDto> groups = new ArrayList<>();

    GroupDto group = createEmptyGroup(6L, 6);

    Set<LessonDto> lessons = createLessonsWithSubjectPerDayViolation(group);

    group.setLessons(lessons);

    groups.add(group);

    return new Individual(groups.toArray());
  }

  private Individual createTimetableWithTeacherTimeslotViolation() {
    List<GroupDto> groups = new ArrayList<>();

    GroupDto group_1 = createEmptyGroup(6L, 6);
    GroupDto group_2 = createEmptyGroup(7L, 7);

    Set<LessonDto> lessons_1 = createLessonsWithoutViolations(group_1);
    Set<LessonDto> lessons_2 = createLessonsWithoutViolations(group_2);
    lessons_2.forEach(lesson -> {
      if (lesson.getTimeSlotDto().getLessonNumber() != 2) {
        lesson.setTeacherDto(new TeacherDto(2L, "Default 2", null, 18));
      }
    });

    group_1.setLessons(lessons_1);
    group_2.setLessons(lessons_2);

    groups.add(group_1);
    groups.add(group_2);

    return new Individual(groups.toArray());
  }

  private Individual createTimetableWithRoomTimeslotViolation() {
    List<GroupDto> groups = new ArrayList<>();

    GroupDto group_1 = createEmptyGroup(6L, 6);
    GroupDto group_2 = createEmptyGroup(7L, 7);

    RoomDto commonRoom = new RoomDto(2L, "Room 2", 1);

    Set<LessonDto> lessons_1 = createLessonsWithoutViolations(group_1);
    lessons_1.forEach(lesson -> {
      if (lesson.getTimeSlotDto().getLessonNumber() == 2) {
        lesson.setRoomDto(commonRoom);
      }
    });
    Set<LessonDto> lessons_2 = createLessonsWithoutViolations(group_2);
    lessons_2.forEach(lesson -> {
      lesson.setTeacherDto(new TeacherDto(2L, "Default 2", null, 18));
      if (lesson.getTimeSlotDto().getLessonNumber() == 2) {
        lesson.setRoomDto(commonRoom);
      }
    });

    group_1.setLessons(lessons_1);
    group_2.setLessons(lessons_2);

    groups.add(group_1);
    groups.add(group_2);

    return new Individual(groups.toArray());
  }

  private Individual createTimetableWithoutViolations() {
    List<GroupDto> groups = new ArrayList<>();

    GroupDto group = createEmptyGroup(5L, 5);

    Set<LessonDto> lessons = createLessonsWithoutViolations(group);

    group.setLessons(lessons);

    groups.add(group);

    return new Individual(groups.toArray());
  }

  private GroupDto createEmptyGroup(long id, int gradeNumber) {
    return GroupDto.builder()
        .id(id)
        .gradeNumber(gradeNumber)
        .shift(1)
        .build();
  }

  private Set<LessonDto> createLessonsWithoutViolations(GroupDto group) {
    Set<LessonDto> lessons = new HashSet<>();

    for (int lessonNum = 1; lessonNum < 8; lessonNum++) {
      TeacherDto teacherDto = getDefaultTeacherDto();
      SubjectDto subjectDto = getSubjectByLessonNumber(lessonNum);
      RoomDto roomDto = getDefaultRoomDto();
      TimeSlotDto timeSlotDto = getTimeSlotByLessonNumber(lessonNum);

      LessonDto lesson = new LessonDto(nextLessonId++, group, teacherDto, subjectDto, roomDto, timeSlotDto);
      lessons.add(lesson);
    }

    return lessons;
  }

  private Set<LessonDto> createLessonsWithSubjectPerDayViolation(GroupDto group) {
    Set<LessonDto> lessons = createLessonsWithoutViolations(group);

    lessons.forEach(lesson -> {
      if (lesson.getTimeSlotDto().getLessonNumber() == 1 ||
          lesson.getTimeSlotDto().getLessonNumber() == 3 ||
          lesson.getTimeSlotDto().getLessonNumber() == 6) {
        lesson.setSubjectDto(getSubjectByLessonNumber(3));
      }
    });

    return lessons;
  }

  private TeacherDto getDefaultTeacherDto() {
    return new TeacherDto(1L, "Default", null, 18);
  }

  private RoomDto getDefaultRoomDto() {
    return new RoomDto(1L, "Default", 2);
  }

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

  private TimeSlotDto getTimeSlotByLessonNumber(int lessonNumber) {
    return switch (lessonNumber) {
      case 1 -> new TimeSlotDto(1L, 1, DayOfWeek.MONDAY, WeekType.BOTH);
      case 2 -> new TimeSlotDto(2L, 2, DayOfWeek.MONDAY, WeekType.BOTH);
      case 3 -> new TimeSlotDto(3L, 3, DayOfWeek.MONDAY, WeekType.BOTH);
      case 4 -> new TimeSlotDto(4L, 4, DayOfWeek.MONDAY, WeekType.BOTH);
      case 5 -> new TimeSlotDto(5L, 5, DayOfWeek.MONDAY, WeekType.BOTH);
      case 6 -> new TimeSlotDto(6L, 6, DayOfWeek.MONDAY, WeekType.BOTH);
      case 7 -> new TimeSlotDto(7L, 7, DayOfWeek.MONDAY, WeekType.BOTH);
      default -> throw new RuntimeException();
    };
  }

}
