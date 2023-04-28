package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TimetableFitnessFunction implements FitnessFunction {

  private static final int SUBJECTS_PER_DAY_FINE = 1;
  private static final int MAX_SAME_SUBJECT_AT_ONE_DAY_COUNT = 2;

  @Override
  public int calculate(Individual individual) {
    int fitness = 0;

    for (int gene = 0; gene < individual.getChromosome().length; gene++) {
      GroupDto group = (GroupDto) individual.getChromosome()[gene];
      fitness += checkSubjectsPerDay(group);
    }
    return fitness;
  }

  private int checkSubjectsPerDay(GroupDto groupDto) {
    return getListOfWorkingDays().stream().reduce(0, (acc, day) -> {
      List<LessonDto> dayLessons = groupDto.getLessons()
          .stream()
          .filter(lesson -> lesson.getTimeSlotDto().getDay().equals(day))
          .toList();

      return acc + checkSubjectsPerSpecificDay(dayLessons);
      }, Integer::sum);
  }

  /* group should have max 2 same subjects per day */
  private int checkSubjectsPerSpecificDay(List<LessonDto> lessons) {
    return lessons.stream().reduce(0, (acc, lesson) -> {
      long matchesCount = lessons
          .stream()
          .filter(l ->
              l.getSubjectDto().equals(lesson.getSubjectDto()) &&
              l.getTeacherDto().equals(lesson.getTeacherDto()))
          .count();

      if (matchesCount > MAX_SAME_SUBJECT_AT_ONE_DAY_COUNT) {
        return acc + SUBJECTS_PER_DAY_FINE;
      }
      return acc;
    }, Integer::sum);
  }

  // consider shift of the group
// TODO:  check room

  // consider shift of the group
// TODO:  check teacher timeslot


  private List<DayOfWeek> getListOfWorkingDays() {
    return Stream.of(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ).collect(Collectors.toList());
  }

}
