package ip91.chui.oleh.algorithm.util;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TimeTableDto;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class IndividualMapper {

  private IndividualMapper() {}

  public static TimeTableDto toTimetableDto(Individual individual) {
    Set<LessonDto> lessons = Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .flatMap(group -> group.getLessons().stream())
        .collect(Collectors.toSet());

    return new TimeTableDto(null, lessons);
  }
}
