package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.teacher.TeacherLimitsDto;
import ip91.chui.oleh.model.dto.teacher.limit.FreeDayLimitDto;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class TeacherFreeDaysFitnessFunctionStepTest {

  private static final int FINE = 2;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private static final int GROUPS_COUNT = 1;
  private static final int LESSONS_COUNT = 3;

  private TeacherFreeDaysFitnessFunctionStep step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    step = new TeacherFreeDaysFitnessFunctionStep(FINE);
    context = new FitnessFunctionContext(new ArrayList<>(), INITIAL_FITNESS_SCORE, null);
  }

  @Test
  void shouldFineEveryLesson() {
    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    TeacherDto teacher = buildTeacherWithFreeDayLimits(Set.of(DayOfWeek.TUESDAY));
    Queue<DayOfWeek> days = new LinkedList<>(
        List.of(DayOfWeek.TUESDAY, DayOfWeek.TUESDAY, DayOfWeek.TUESDAY));

    setTeacherAndDayForLessons(individual, teacher, days);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE + (LESSONS_COUNT * FINE), context.getFitnessScore());
  }

  @Test
  void shouldFineOnlyFewLessons() {
    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    TeacherDto teacher = buildTeacherWithFreeDayLimits(Set.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY));
    Queue<DayOfWeek> days = new LinkedList<>(
        List.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));

    setTeacherAndDayForLessons(individual, teacher, days);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE + (FINE * 2), context.getFitnessScore());
  }

  @Test
  void shouldNotFine() {
    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    TeacherDto teacher = buildTeacherWithFreeDayLimits(Collections.emptySet());
    Queue<DayOfWeek> days = new LinkedList<>(
        List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.FRIDAY));

    setTeacherAndDayForLessons(individual, teacher, days);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE, context.getFitnessScore());
  }

  private TeacherDto buildTeacherWithFreeDayLimits(Set<DayOfWeek> freeDays) {
    Set<FreeDayLimitDto> freeDayLimits = freeDays.stream()
        .map(day -> new FreeDayLimitDto(null, day))
        .collect(Collectors.toSet());

    TeacherLimitsDto limits = new TeacherLimitsDto();
    limits.setFreeDayLimits(freeDayLimits);

    TeacherDto teacher = new TeacherDto();
    teacher.setLimits(limits);

    return teacher;
  }

  private void setTeacherAndDayForLessons(
      Individual individual, TeacherDto teacher, Queue<DayOfWeek> days) {

    Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .forEach(group -> group.getLessons().forEach(lesson -> {
          lesson.setTeacherDto(teacher);
          lesson.getTimeSlotDto().setDay(days.poll()); }));
  }
}