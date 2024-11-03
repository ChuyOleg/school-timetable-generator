package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.RoomDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;

// todo: 05/10/24 I need to think about renaming, the name is too big for now.
// todo: 05/10/24 Do I need any Logs here?
@RequiredArgsConstructor
public class RoomTimeslotCollisionsFitnessFunctionStep implements FitnessFunctionStep {

  // todo: 01/10/24 school shift does not guarantee that room does not have collisions.
  //  e.g: shift_1_lesson_6 can = shift_2_lesson_1
  private static final int SHIFT_1 = 1;
  private static final int SHIFT_2 = 2;

  private final int roomMaxLessonsAtSameTimeFine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    List<LessonDto> shift1Lessons = getLessonsFromIndividualByShift(individual, (group -> group.getShift() == SHIFT_1));
    List<LessonDto> shift2Lessons = getLessonsFromIndividualByShift(individual, (group -> group.getShift() == SHIFT_2));

    int fitnessScore = 0;

    fitnessScore += y(shift1Lessons);
    fitnessScore += y(shift2Lessons);

    context.increaseFitnessScore(fitnessScore);
  }

  private int y(List<LessonDto> lessons) {
    Map<RoomDto, Map<TimeSlotDto, Integer>> lessonsCountPerTimeslotPerRoom = new HashMap<>();

    lessons.stream()
        .filter(lesson -> !Objects.isNull(lesson.getRoomDto()))
        .forEach(lesson -> {
          Map<TimeSlotDto, Integer> map = lessonsCountPerTimeslotPerRoom.getOrDefault(lesson.getRoomDto(), new HashMap<>());
          lessonsCountPerTimeslotPerRoom.putIfAbsent(lesson.getRoomDto(), map);

          int currentLessonsCount = map.getOrDefault(lesson.getTimeSlotDto(), 0);
          map.put(lesson.getTimeSlotDto(), ++currentLessonsCount);
        });

    return lessonsCountPerTimeslotPerRoom.entrySet().stream()
        .flatMap(entry -> entry.getValue().values().stream()
            .filter(lessonsCount -> lessonsCount > entry.getKey().getCapacity()))
        .map(fine -> fine * roomMaxLessonsAtSameTimeFine)
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
