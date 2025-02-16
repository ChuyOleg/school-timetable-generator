package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 * This class is responsible for calculating fitness penalties
 * based on the occurrence of the same subject within a single day for each group in the timetable.
 * Unique subject has a unique combination of [subject.id + "_" + teacher.id].
 *
 * @author Oleh Chui
 */
// ToDo: 17/11/24 artificial mutation can be somehow used here
@RequiredArgsConstructor
public class SameSubjectsPerDayFitnessFunctionStep implements FitnessFunctionStep {

  private static final String UNIQUE_SUBJECT_FORMAT = "%d_%d";

  private final int sameSubjectsPerDayLimit;
  private final int sameSubjectPerDayFine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    List<GroupDto> groups = castIndividualToListOfGroups(individual);
    int fitnessScore = calculateFitnessScoreForGroups(groups);

    context.increaseFitnessScore(fitnessScore);
  }

  private List<GroupDto> castIndividualToListOfGroups(Individual individual) {
    return Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .collect(Collectors.toList());
  }

  private int calculateFitnessScoreForGroups(List<GroupDto> groups) {
    return groups.stream()
        .map(this::calculateFitnessScoreForGroup)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private int calculateFitnessScoreForGroup(GroupDto groupDto) {
    Map<DayOfWeek, List<LessonDto>> lessonsByDay = groupDto.getLessons().stream()
        .collect(Collectors.groupingBy(lessonDto -> lessonDto.getTimeSlotDto().getDay()));

    return lessonsByDay.values().stream()
        .map(this::calculateFitnessScoreForLessons)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private int calculateFitnessScoreForLessons(List<LessonDto> lessons) {
    Map<String, List<LessonDto>> lessonsBySubjectAndTeacher = lessons.stream()
        .collect(Collectors.groupingBy(lessonDto -> String.format(UNIQUE_SUBJECT_FORMAT,
            lessonDto.getSubjectDto().getId(), lessonDto.getTeacherDto().getId())));

    return lessonsBySubjectAndTeacher.values().stream()
        .mapToInt(List::size)
        .filter(sameSubjectsCount -> sameSubjectsCount > sameSubjectsPerDayLimit)
        .map(sameSubjectsCount -> sameSubjectsCount * sameSubjectPerDayFine)
        .sum();
  }
}
