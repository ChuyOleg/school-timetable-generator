package ip91.chui.oleh.algorithm.fitnessFunction.utils;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import java.util.Arrays;
import java.util.List;

final public class LessonsExtractor {

  private LessonsExtractor() {}

  public static List<LessonDto> fromIndividual(Individual individual) {
    return Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .flatMap(group -> group.getLessons().stream())
        .toList();
  }
}
