package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.room.RoomLimitDto;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

// ToDo: 17/11/24 artificial mutation can be easily used here
@RequiredArgsConstructor
public class RoomReservationFitnessFunctionStep implements FitnessFunctionStep {

  private final int fine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateRoomReservationFitnessScore(context.getLessons());

    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateRoomReservationFitnessScore(List<LessonDto> lessons) {
    long violationsCount = lessons.stream()
        .filter(this::isLessonInsideRoomWithReservationLimits)
        .filter(this::doesLessonViolateRoomLimits)
        .count();

    return (int) violationsCount * fine;
  }

  private boolean doesLessonViolateRoomLimits(LessonDto lesson) {
    return lesson.getRoomDto().getRoomLimitDtoSet().stream()
        .anyMatch(roomLimit -> isLessonInsideRoomLimit(lesson, roomLimit));
  }

  private boolean isLessonInsideRoomLimit(LessonDto lesson, RoomLimitDto roomLimit) {
    var lessonTimeslot = lesson.getTimeSlotDto();

    return roomLimit.getDay().equals(lessonTimeslot.getDay())
        && roomLimit.getLessonNumberFrom() <= lessonTimeslot.getLessonNumber()
        && roomLimit.getLessonNumberTo() >= lessonTimeslot.getLessonNumber();
  }

  private boolean isLessonInsideRoomWithReservationLimits(LessonDto lesson) {
    var room = lesson.getRoomDto();

    return !Objects.isNull(room)
        && !Objects.isNull(room.getRoomLimitDtoSet())
        && !room.getRoomLimitDtoSet().isEmpty();
  }
}
