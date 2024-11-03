package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class TeacherTimeslotCollisionsFitnessFunctionStepTest {

  private static final int TEACHER_MAX_LESSONS_AT_SAME_TIME_LIMIT = 1;
  private static final int TEACHER_MAX_LESSONS_AT_SAME_TIME_FINE = 1;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private TeacherTimeslotCollisionsFitnessFunctionStep step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    step = new TeacherTimeslotCollisionsFitnessFunctionStep(
        TEACHER_MAX_LESSONS_AT_SAME_TIME_LIMIT, TEACHER_MAX_LESSONS_AT_SAME_TIME_FINE);
    context = new FitnessFunctionContext();
    context.setFitnessScore(INITIAL_FITNESS_SCORE);
  }

  @Test
  void shouldX() {
    final int groupCount = 3;
    final int lessonsCount = 6;

    Individual individual = IndividualFactoryUtil.individualWithUniqueSubjectIdsPerGroup(groupCount, lessonsCount);

    step.calculate(individual, context);
    assertEquals(INITIAL_FITNESS_SCORE + groupCount * lessonsCount, context.getFitnessScore());
  }
}