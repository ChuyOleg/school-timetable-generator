package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// todo: 05/10/24 I need to think about renaming, the name is too big for now.
// todo: 05/10/24 Do I need any Logs here?
@Slf4j
@RequiredArgsConstructor
public class TeacherTimeslotCollisionsFitnessFunctionStep implements FitnessFunctionStep {

  // todo: 01/10/24 school shift does not guarantee that teacher does not have collisions.
  //  e.g: shift_1_lesson_6 can = shift_2_lesson_1
  private static final int SHIFT_1 = 1;
  private static final int SHIFT_2 = 2;

  // todo: 01/10/24 teacher can have more than 1 lesson at specific conditions:
  //  e.g: teacher who drives subgroup can have subgroups from different groups at the same lesson.
  //    e.g: labor work or physical culture teachers (Biluk Bohdan explained this moment)

  private final int teacherMaxLessonsAtSameTimeLimit;
  private final int teacherMaxLessonsAtSameTimeFine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    List<LessonDto> shift1Lessons = getLessonsFromIndividualByShift(individual, (group -> group.getShift() == SHIFT_1));
    List<LessonDto> shift2Lessons = getLessonsFromIndividualByShift(individual, (group -> group.getShift() == SHIFT_2));

    int fitnessScore = 0;

    fitnessScore += x(shift1Lessons);
    fitnessScore += x(shift2Lessons);

    context.increaseFitnessScore(fitnessScore);
  }

  // I can count number of operations using Logs (add counter and print at the end)
  // todo: 05/10/24 I will refactor in a while
  // todo: 05/10/24 could some part logic be shared with RoomFitnessFunctionStep?
  private int x(List<LessonDto> lessons) {
    Map<TeacherDto, Map<TimeSlotDto, Integer>> lessonsCountPerTimeslotPerTeacher = new HashMap<>();

    lessons.forEach(lesson -> {
      Map<TimeSlotDto, Integer> map = lessonsCountPerTimeslotPerTeacher.getOrDefault(lesson.getTeacherDto(), new HashMap<>());
      lessonsCountPerTimeslotPerTeacher.putIfAbsent(lesson.getTeacherDto(), map);

      int currentLessonsCount = map.getOrDefault(lesson.getTimeSlotDto(), 0);
      map.put(lesson.getTimeSlotDto(), ++currentLessonsCount);
    });

    return lessonsCountPerTimeslotPerTeacher.values().stream()
        .flatMap(lessonsCountPerTimeslot -> lessonsCountPerTimeslot.values().stream())
        .filter(lessonsCount -> lessonsCount > teacherMaxLessonsAtSameTimeLimit)
        .map(fine -> fine * teacherMaxLessonsAtSameTimeFine)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private List<LessonDto> getLessonsFromIndividualByShift(Individual individual, Predicate<GroupDto> predicate) {
    return Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .filter(predicate)
        .flatMap(group -> group.getLessons().stream())
        .toList();
  }
}
