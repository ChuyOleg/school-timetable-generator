package ip91.chui.oleh.algorithm.fitnessFunction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.room.RoomLimitDto;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class RoomReservationFitnessFunctionStepTest {

  private static final int FINE = 2;
  private static final int INITIAL_FITNESS_SCORE = 10;

  private static final int GROUPS_COUNT = 1;
  private static final int LESSONS_COUNT = 8;
  private static final DayOfWeek DEFAULT_DAY = DayOfWeek.TUESDAY;

  private RoomReservationFitnessFunctionStep step;
  private FitnessFunctionContext context;

  @BeforeEach
  void setUp() {
    step = new RoomReservationFitnessFunctionStep(FINE);
    context = new FitnessFunctionContext(new ArrayList<>(), INITIAL_FITNESS_SCORE, null);
  }

  @ParameterizedTest
  @MethodSource("provideArguments")
  void shouldProperlyCalculateFitnessScore(
      Set<LimitHolder> limits, Set<Integer> lessonNumbersForRoom, int expectedScore) {

    Individual individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    RoomDto room = buildRoomWithDesiredLimits(limits);

    setDayAndLessonNumbersAndRoomForLessons(individual, room, lessonNumbersForRoom);

    context.getLessons().addAll(LessonsExtractor.fromIndividual(individual));
    step.calculate(individual, context);

    assertEquals(INITIAL_FITNESS_SCORE + expectedScore, context.getFitnessScore());
  }

  private static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(Set.of(new LimitHolder(DEFAULT_DAY, 3, 5)), Set.of(1, 3, 5, 7), FINE * 2),
        Arguments.of(Set.of(new LimitHolder(DEFAULT_DAY, 1, 3), new LimitHolder(DEFAULT_DAY, 7, 8)), Set.of(1, 3, 5, 7), FINE * 3),
        Arguments.of(Set.of(new LimitHolder(DEFAULT_DAY, 3, 5)), Set.of(1, 2, 6, 7, 8), 0),
        Arguments.of(Set.of(new LimitHolder(DayOfWeek.FRIDAY, 3, 5)), Set.of(1, 3, 5, 7), 0));
  }

  private RoomDto buildRoomWithDesiredLimits(Set<LimitHolder> limits) {
    Set<RoomLimitDto> roomLimits = limits.stream()
        .map(limit -> new RoomLimitDto(null, limit.day, 1, limit.lessonFrom, limit.lessonTo))
        .collect(Collectors.toSet());

    RoomDto room = new RoomDto();
    room.setId(1000L);
    room.setRoomLimitDtoSet(roomLimits);

    return room;
  }

  private void setDayAndLessonNumbersAndRoomForLessons(
      Individual individual, RoomDto room, Set<Integer> lessonNumbersForRoom) {

    AtomicInteger lessonNumber = new AtomicInteger(1);

    Arrays.stream(individual.getChromosome())
        .map(gene -> (GroupDto) gene)
        .flatMap(group -> group.getLessons().stream())
        .peek(lesson -> {
          lesson.getTimeSlotDto().setDay(RoomReservationFitnessFunctionStepTest.DEFAULT_DAY);
          lesson.getTimeSlotDto().setLessonNumber(lessonNumber.getAndIncrement()); })
        .filter(lesson -> lessonNumbersForRoom.contains(lesson.getTimeSlotDto().getLessonNumber()))
        .forEach(lesson -> lesson.setRoomDto(room));
  }

  private record LimitHolder(DayOfWeek day, int lessonFrom, int lessonTo) {}
}