package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.teacher.TeacherLimitsDto;
import ip91.chui.oleh.model.dto.teacher.limit.DesiredPeriodLimitDto;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class TeacherDesiredPeriodsFitnessFunctionStepTest {

  private static final int FINE = 2;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private static final int GROUP_COUNT = 1;
  private static final DayOfWeek DEFAULT_DAY = DayOfWeek.MONDAY;

  private TeacherDesiredPeriodsFitnessFunctionStep step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    step = new TeacherDesiredPeriodsFitnessFunctionStep(FINE);
    context = new FitnessFunctionContext(new ArrayList<>(), INITIAL_FITNESS_SCORE, null);
  }

  @ParameterizedTest
  @MethodSource("provideArguments")
  void shouldProperlyCalculateFitnessScore(
      DayOfWeek day, int lessonsCount, int lessonFrom, int lessonTo, int expectedScore) {

    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUP_COUNT, lessonsCount);

    TeacherDto teacher = buildTeacherWithDesiredPeriodLimits(day, lessonFrom, lessonTo);

    setDayAndTeacherForLessons(individual, teacher);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE + expectedScore, context.getFitnessScore());
  }

  private static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(DayOfWeek.MONDAY, 8, 3, 5, FINE * 5),
        Arguments.of(DayOfWeek.MONDAY, 7, 3, 5, FINE * 4),
        Arguments.of(DayOfWeek.MONDAY, 6, 1, 2, FINE * 4),
        Arguments.of(DayOfWeek.TUESDAY, 8, 3, 5, 0) );
  }

  private TeacherDto buildTeacherWithDesiredPeriodLimits(DayOfWeek day, int lessonFrom, int lessonTo) {
    DesiredPeriodLimitDto desiredPeriodLimit = new DesiredPeriodLimitDto(null, day, 1, lessonFrom, lessonTo);

    TeacherLimitsDto limits = new TeacherLimitsDto();
    limits.setDesiredPeriodLimits(Set.of(desiredPeriodLimit));

    TeacherDto teacher = new TeacherDto();
    teacher.setLimits(limits);

    return teacher;
  }

  private void setDayAndTeacherForLessons(Individual individual, TeacherDto teacher) {
    AtomicInteger lessonNumber = new AtomicInteger(1);

    Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .forEach(group -> group.getLessons()
            .forEach(lesson -> {
              lesson.getTimeSlotDto().setLessonNumber(lessonNumber.getAndIncrement());
              lesson.getTimeSlotDto().setDay(DEFAULT_DAY);
              lesson.setTeacherDto(teacher); }));
  }
}