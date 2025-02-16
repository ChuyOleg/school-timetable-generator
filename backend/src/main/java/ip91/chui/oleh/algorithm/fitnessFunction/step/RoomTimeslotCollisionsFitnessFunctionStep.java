package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsMapper;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomTimeslotCollisionsFitnessFunctionStep implements FitnessFunctionStep {

  private final int roomMaxLessonsAtSameTimeFine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateInternal(context.getLessons());
    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateInternal(List<LessonDto> lessons) {
    Map<RoomDto, Map<TimeSlotDto, Integer>> lessonsCountPerTimeslotByRoom =
        LessonsMapper.toLessonPerTimeslotByRoom(lessons);

    return lessonsCountPerTimeslotByRoom.entrySet().stream()
        .flatMap(entry -> entry.getValue().values().stream()
            .filter(lessonsCount -> lessonsCount > entry.getKey().getCapacity()))
        .map(violationsCount -> violationsCount * roomMaxLessonsAtSameTimeFine)
        .mapToInt(Integer::intValue)
        .sum();
  }
}
