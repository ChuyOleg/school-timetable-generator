package ip91.chui.oleh.algorithm.fitnessFunction.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

final class LessonsExtractorTest {

  private static final int GROUPS_COUNT = 3;
  private static final int LESSONS_COUNT = 7;

  @Test
  void shouldProperlyExtractLessonFromIndividual() {
    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    List<LessonDto> lessons = LessonsExtractor.fromIndividual(individual);

    assertThat(lessons).hasSize(GROUPS_COUNT * LESSONS_COUNT);
    assertTrue(lessons.stream().allMatch(Objects::nonNull));
  }
}