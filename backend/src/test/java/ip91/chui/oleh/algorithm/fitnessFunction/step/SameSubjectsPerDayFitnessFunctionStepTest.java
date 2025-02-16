package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static ip91.chui.oleh.IndividualFactoryUtil.individualWithDuplicatedSubjectIdsPerGroup;
import static ip91.chui.oleh.IndividualFactoryUtil.individualWithUniqueSubjectIdsPerGroup;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.algorithm.DefaultDtoFactoryUtil;
import ip91.chui.oleh.algorithm.DtoFactoryUtil;
import ip91.chui.oleh.algorithm.ModelUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class SameSubjectsPerDayFitnessFunctionStepTest {

  private static final int DEFAULT_SAME_SUBJECTS_PER_DAY_LIMIT = 2;
  private static final int DEFAULT_SAME_SUBJECTS_PER_DAY_FINE = 3;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private SameSubjectsPerDayFitnessFunctionStep step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    step = new SameSubjectsPerDayFitnessFunctionStep(
        DEFAULT_SAME_SUBJECTS_PER_DAY_LIMIT, DEFAULT_SAME_SUBJECTS_PER_DAY_FINE);
    context = new FitnessFunctionContext(new ArrayList<>(), INITIAL_FITNESS_SCORE, null);
  }

  @ParameterizedTest
  @MethodSource("provideStraightforwardIndividualsForFitnessScoreCalculation")
  void shouldCalculateFitnessScoreProperlyForStraightforwardIndividual(
      Individual individual, long expectedFitnessScore) {

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);
    assertEquals(INITIAL_FITNESS_SCORE + expectedFitnessScore, context.getFitnessScore());
  }

  private static Stream<Arguments> provideStraightforwardIndividualsForFitnessScoreCalculation() {
    final int groupsCount = 3;
    final int lessonsCount = DEFAULT_SAME_SUBJECTS_PER_DAY_LIMIT + 4;
    final int duplicatesCount = DEFAULT_SAME_SUBJECTS_PER_DAY_LIMIT + 2;

    return Stream.of(
        Arguments.of(individualWithUniqueSubjectIdsPerGroup(groupsCount, lessonsCount), 0L),
        Arguments.of(individualWithDuplicatedSubjectIdsPerGroup(groupsCount, lessonsCount, DEFAULT_SAME_SUBJECTS_PER_DAY_LIMIT), 0),
        Arguments.of(individualWithDuplicatedSubjectIdsPerGroup(groupsCount, lessonsCount, duplicatesCount),
            groupsCount * duplicatesCount * DEFAULT_SAME_SUBJECTS_PER_DAY_FINE));
  }

  @Test
  void shouldNotAddFineWhenSubgroupLessonHasSameSubjectButDifferentTeachers() {
    Individual individual = individualWithTwoSubgroupLessons();

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE, context.getFitnessScore());
  }

  private Individual individualWithTwoSubgroupLessons() {
    final long subjectId = 1L;
    final long teacher1Id = 1L;
    final long teacher2Id = 2L;

    Set<LessonDto> subgroupLesson1 = DefaultDtoFactoryUtil.subGroupsLesson(
        1L, 2L, subjectId, teacher1Id, teacher2Id);
    Set<LessonDto> subgroupLesson2 = DefaultDtoFactoryUtil.subGroupsLesson(
        3L, 4L, subjectId, teacher1Id, teacher2Id);

    Set<LessonDto> lessons = Stream.concat(subgroupLesson1.stream(), subgroupLesson2.stream())
        .collect(Collectors.toSet());

    GroupDto group = DtoFactoryUtil.groupWithUniqueSubjectIds(1L, 0);
    ModelUtil.linkGroupWithLessons(group, lessons);

    return new Individual(List.of(group).toArray());
  }
}