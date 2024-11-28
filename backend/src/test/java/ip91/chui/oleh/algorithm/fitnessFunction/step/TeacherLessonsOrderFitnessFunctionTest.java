package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.teacher.TeacherLimitsDto;
import ip91.chui.oleh.model.dto.teacher.limit.LessonsOrderLimitDto;
import ip91.chui.oleh.model.enumeration.ImportanceLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class TeacherLessonsOrderFitnessFunctionTest {

  private static final int MEDIUM_FINE = 2;
  private static final int HIGH_FINE = 3;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private static final int GROUPS_COUNT = 1;
  private static final int LESSONS_COUNT = 8;

  private TeacherLessonsOrderFitnessFunction step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    this.step = new TeacherLessonsOrderFitnessFunction(MEDIUM_FINE, HIGH_FINE);
    this.context = new FitnessFunctionContext(new ArrayList<>(), INITIAL_FITNESS_SCORE, null);
  }

  @ParameterizedTest
  @MethodSource("provideArguments")
  void shouldProperlyCalculateFitnessScore(
      ImportanceLevel importanceLevel, Set<Integer> lessonNumbers, int expectedScore) {

    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    TeacherDto teacher = buildTeacherWithLessonsOrderLimit(importanceLevel);

    setTeacherForSpecificLessons(individual, teacher, lessonNumbers);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE + expectedScore, context.getFitnessScore());
  }

  private static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(ImportanceLevel.LOW, Set.of(1, 3, 6), 0),
        Arguments.of(ImportanceLevel.MEDIUM,Set.of(1, 3, 6), MEDIUM_FINE * 3),
        Arguments.of(ImportanceLevel.HIGH, Set.of(1, 3, 6), HIGH_FINE * 3),

        Arguments.of(ImportanceLevel.LOW, Set.of(2, 7), 0),
        Arguments.of(ImportanceLevel.MEDIUM, Set.of(2, 7), MEDIUM_FINE * 4),
        Arguments.of(ImportanceLevel.HIGH, Set.of(2, 7), HIGH_FINE * 4),

        Arguments.of(ImportanceLevel.LOW, Set.of(5, 6, 7, 8), 0),
        Arguments.of(ImportanceLevel.MEDIUM, Set.of(5, 6, 7, 8), 0),
        Arguments.of(ImportanceLevel.HIGH, Set.of(5, 6, 7, 8), 0) );
  }

  private TeacherDto buildTeacherWithLessonsOrderLimit(ImportanceLevel importanceLevel) {
    TeacherLimitsDto limits = new TeacherLimitsDto();
    limits.setLessonsOrderLimit(new LessonsOrderLimitDto(null, importanceLevel));

    TeacherDto teacher = new TeacherDto();
    teacher.setId(1000L);
    teacher.setLimits(limits);

    return teacher;
  }

  private void setTeacherForSpecificLessons(
      Individual individual, TeacherDto teacher, Set<Integer> lessonNumbers) {

    AtomicInteger lessonNumber = new AtomicInteger(1);

    Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .forEach(group -> group.getLessons()
            .forEach(lesson -> {
              if (lessonNumbers.contains(lessonNumber.get())) {
                lesson.setTeacherDto(teacher);
              }

              lesson.getTimeSlotDto().setLessonNumber(lessonNumber.getAndIncrement()); }));
  }
}