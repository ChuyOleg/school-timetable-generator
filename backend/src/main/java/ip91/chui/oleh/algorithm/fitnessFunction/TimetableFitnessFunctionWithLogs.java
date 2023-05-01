package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.RoomDto;
import ip91.chui.oleh.model.dto.TeacherDto;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimetableFitnessFunctionWithLogs implements FitnessFunction {

  private static final int SUBJECTS_PER_DAY_FINE = 1;
  private static final int TEACHER_MAX_LESSONS_AT_SAME_TIME_FINE = 1;
  private static final int ROOM_MAX_LESSONS_AT_SAME_TIME_FINE = 1;
  private static final int MAX_SAME_SUBJECT_AT_ONE_DAY_COUNT = 2;
  private static final int MAX_TEACHER_HAS_LESSONS_AT_SAME_TIME = 1;
  private static final int SHIFT_1 = 1;
  private static final int SHIFT_2 = 2;

  @Override
  public int calculate(Individual individual) {
    int fitness = 0;

    List<LessonDto> shift1Lessons = getLessonsFromIndividualByShift(individual, (group -> group.getShift() == SHIFT_1));
    List<LessonDto> shift2Lessons = getLessonsFromIndividualByShift(individual, (group -> group.getShift() == SHIFT_2));

    fitness += checkTimeSlotCollision(shift1Lessons);
    fitness += checkTimeSlotCollision(shift2Lessons);

    for (int gene = 0; gene < individual.getChromosome().length; gene++) {
      GroupDto group = (GroupDto) individual.getChromosome()[gene];
      fitness += checkSubjectsPerDay(group);
    }
    return fitness;
  }

  private int checkTimeSlotCollision(List<LessonDto> lessons) {
    return lessons.stream().reduce(0, (acc, lesson) -> {
      List<LessonDto> sameDayAndLessonNumberLessons = lessons
          .stream()
          .filter(l -> l.getTimeSlotDto().equals(lesson.getTimeSlotDto()))
          .toList();

      return acc +
          checkTeacherTimeSlotCollision(lesson.getTeacherDto(), sameDayAndLessonNumberLessons) +
          checkRoomTimeSlotCollision(lesson.getRoomDto(), sameDayAndLessonNumberLessons);
    }, Integer::sum);
  }

  /* teacher should have max 1 lesson at same time */
  private int checkTeacherTimeSlotCollision(TeacherDto teacher, List<LessonDto> lessons) {
    long sameTeacherCount = lessons
        .stream()
        .filter(lesson -> lesson.getTeacherDto().equals(teacher))
        .count();

    if (sameTeacherCount > MAX_TEACHER_HAS_LESSONS_AT_SAME_TIME) {
      System.out.println("--------------------------");
      System.out.println("Teacher fine: teacherId: " + lessons.stream().filter(lesson -> lesson.getTeacherDto().equals(teacher)).toList().get(0).getTeacherDto().getId() + " | timeSlot: " + lessons.get(0).getTimeSlotDto());
      System.out.println("Lessons: ");
      lessons.stream().filter(lesson -> lesson.getTeacherDto().equals(teacher)).forEach(l -> System.out.println(l.getTeacherDto().getId() + " | "));
      System.out.println("--------------------------");
      return TEACHER_MAX_LESSONS_AT_SAME_TIME_FINE;
    }

    return 0;
  }

  private int checkRoomTimeSlotCollision(RoomDto room, List<LessonDto> lessons) {
    if (room == null) {
      return 0;
    }

    long sameRoomCount = lessons
        .stream()
        .filter(lesson -> room.equals(lesson.getRoomDto()))
        .count();

    if (sameRoomCount > room.getCapacity()) {
      System.out.println("Room fine: " + " | timeSlot: " + lessons.get(0).getTimeSlotDto());
      return ROOM_MAX_LESSONS_AT_SAME_TIME_FINE;
    }

    return 0;
  }

  private int checkSubjectsPerDay(GroupDto groupDto) {
    return getListOfWorkingDays().stream().reduce(0, (acc, day) -> {
      List<LessonDto> dayLessons = groupDto.getLessons()
          .stream()
          .filter(lesson -> lesson.getTimeSlotDto().getDay().equals(day))
          .toList();

      return acc + checkSubjectsPerSpecificDay(dayLessons);
    }, Integer::sum);
  }

  /* group should have max 2 same subjects per day */
  private int checkSubjectsPerSpecificDay(List<LessonDto> lessons) {
    return lessons.stream().reduce(0, (acc, lesson) -> {
      long matchesCount = lessons
          .stream()
          .filter(l ->
              l.getSubjectDto().equals(lesson.getSubjectDto()) &&
                  l.getTeacherDto().equals(lesson.getTeacherDto()))
          .count();

      if (matchesCount > MAX_SAME_SUBJECT_AT_ONE_DAY_COUNT) {
        System.out.println("Subject fine: subjectId: " + lesson.getSubjectDto().getId() + " | timeSlot: " + lesson.getTimeSlotDto());
        return acc + SUBJECTS_PER_DAY_FINE;
      }
      return acc;
    }, Integer::sum);
  }

  private List<LessonDto> getLessonsFromIndividualByShift(Individual individual, Predicate<GroupDto> predicate) {
    return Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .filter(predicate)
        .flatMap(group -> group.getLessons().stream())
        .toList();
  }

  private List<DayOfWeek> getListOfWorkingDays() {
    return Stream.of(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ).collect(Collectors.toList());
  }

}
