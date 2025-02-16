package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.teacher.TeacherLimitsDto;
import ip91.chui.oleh.model.dto.teacher.limit.MaxLessonsLimitDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class TeacherMaxLessonsPerDayFitnessFunctionStepTest {

  private static final int FINE = 2;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private static final int GROUPS_COUNT = 1;

  private TeacherMaxLessonsPerDayFitnessFunctionStep step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    step = new TeacherMaxLessonsPerDayFitnessFunctionStep(FINE);
    context = new FitnessFunctionContext(new ArrayList<>(), INITIAL_FITNESS_SCORE, null);
  }

  @ParameterizedTest
  @MethodSource("provideArguments")
  void shouldProperlyCalculateFitnessScore(int lessonsCount, int maxLessonsPerDayCount) {
    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, lessonsCount);

    TeacherDto teacher = buildTeacherWithMaxLessonsPerDayLimit(maxLessonsPerDayCount);

    setTeacherForLessons(individual, teacher);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    int difference = lessonsCount > maxLessonsPerDayCount ? lessonsCount - maxLessonsPerDayCount : 0;
    assertEquals(INITIAL_FITNESS_SCORE + (difference * FINE), context.getFitnessScore());
  }

  private static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(5, 3),
        Arguments.of(6, 2),
        Arguments.of(3, 3),
        Arguments.of(3, 5) );
  }

  private TeacherDto buildTeacherWithMaxLessonsPerDayLimit(int maxLessonsPerDayCount) {
    TeacherLimitsDto limits = new TeacherLimitsDto();
    limits.setMaxLessonsLimit(new MaxLessonsLimitDto(null, maxLessonsPerDayCount));

    TeacherDto teacher = new TeacherDto();
    teacher.setLimits(limits);

    return teacher;
  }

  private void setTeacherForLessons(Individual individual, TeacherDto teacher) {
    Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .forEach(group -> group.getLessons()
            .forEach(lesson -> lesson.setTeacherDto(teacher)));
  }
}