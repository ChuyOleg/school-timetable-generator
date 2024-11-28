package ip91.chui.oleh.algorithm.fitnessFunction.utils;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.dto.room.RoomLimitDto;
import ip91.chui.oleh.model.dto.teacher.limit.DesiredPeriodLimitDto;
import ip91.chui.oleh.model.entity.ShiftsIntersection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class SecondShiftLessonNumbersTransformer {

  private static final int FIRST_LESSON_NUMBER = 1;
  private static final int LAST_LESSON_NUMBER = 8;
  private static final int NO_INTERSECTION_OFFSET = 8;
  private static final int SECOND_SHIFT = 2;

  public void transform(List<LessonDto> lessons, List<ShiftsIntersection> intersections) {
    transformInternal(lessons, intersections, Order.FORWARD);
  }

  public void rollbackTransformation(List<LessonDto> lessons, List<ShiftsIntersection> intersections) {
    transformInternal(lessons, intersections, Order.REVERSE);
  }

  private void transformInternal(
      List<LessonDto> lessons, List<ShiftsIntersection> intersections, Order order) {

    Map<Integer, Integer> mapping = generateMapping(intersections, order);

    transformInternalLessonsTimeslots(lessons, mapping);
    transformInternalRoomLimits(lessons, mapping);
    transformInternalTeacherDesiredPeriodLimits(lessons, mapping);
  }

  private void transformInternalLessonsTimeslots(
      List<LessonDto> lessons, Map<Integer, Integer> mapping) {

    lessons.stream()
        .filter(lesson -> lesson.getGroupDto().getShift() == SECOND_SHIFT)
        .map(LessonDto::getTimeSlotDto)
        .collect(Collectors.toSet())
        .forEach(timeslot -> transformLessonNumberByMapping(timeslot, mapping));
  }

  private void transformInternalRoomLimits(
      List<LessonDto> lessons, Map<Integer, Integer> mapping) {

    lessons.stream()
        .filter(lesson -> Objects.nonNull(lesson.getRoomDto()))
        .filter(lesson -> Objects.nonNull(lesson.getRoomDto().getRoomLimitDtoSet()))
        .flatMap(lesson -> lesson.getRoomDto().getRoomLimitDtoSet().stream())
        .filter(roomLimit -> roomLimit.getShift() == SECOND_SHIFT)
        .collect(Collectors.toSet())
        .forEach(roomLimit -> transformLessonNumbersForRoomLimitByMapping(roomLimit, mapping));
  }

  private void transformInternalTeacherDesiredPeriodLimits(
      List<LessonDto> lessons, Map<Integer, Integer> mapping) {

    lessons.stream()
        .filter(lesson -> Objects.nonNull(lesson.getTeacherDto().getLimits()))
        .filter(lesson -> Objects.nonNull(lesson.getTeacherDto().getLimits().getDesiredPeriodLimits()))
        .flatMap(lesson -> lesson.getTeacherDto().getLimits().getDesiredPeriodLimits().stream())
        .filter(desiredPeriodLimit -> desiredPeriodLimit.getShift() == SECOND_SHIFT)
        .collect(Collectors.toSet())
        .forEach(desiredPeriodLimit -> transformLessonNumbersForTeacherDesiredPeriodLimitByMapping(
            desiredPeriodLimit, mapping));
  }

  private void transformLessonNumberByMapping(TimeSlotDto timeslot, Map<Integer, Integer> mapping) {
    timeslot.setLessonNumber(mapping.get(timeslot.getLessonNumber()));
  }

  private void transformLessonNumbersForRoomLimitByMapping(
      RoomLimitDto roomLimit, Map<Integer, Integer> mapping) {

    roomLimit.setLessonNumberFrom(mapping.get(roomLimit.getLessonNumberFrom()));
    roomLimit.setLessonNumberTo(mapping.get(roomLimit.getLessonNumberTo()));
  }

  private void transformLessonNumbersForTeacherDesiredPeriodLimitByMapping(
      DesiredPeriodLimitDto teacherDesiredPeriodLimit, Map<Integer, Integer> mapping) {

    teacherDesiredPeriodLimit.setLessonFrom(mapping.get(teacherDesiredPeriodLimit.getLessonFrom()));
    teacherDesiredPeriodLimit.setLessonTo(mapping.get(teacherDesiredPeriodLimit.getLessonTo()));
  }

  private Map<Integer, Integer> generateMapping(List<ShiftsIntersection> intersections, Order order) {
    if (intersections.isEmpty()) {
      return generateMappingByOffset(NO_INTERSECTION_OFFSET, order);
    }

    ShiftsIntersection shiftTwoFirstLessonNumberIntersection = intersections.stream()
        .filter(intersection -> intersection.getShiftTwoLessonNumber() == FIRST_LESSON_NUMBER)
        .findFirst().orElseThrow(RuntimeException::new);

    int offSet = shiftTwoFirstLessonNumberIntersection.getShiftOneLessonNumber() - 1;

    return generateMappingByOffset(offSet, order);
  }

  private Map<Integer, Integer> generateMappingByOffset(int offset, Order order) {
    return IntStream.rangeClosed(FIRST_LESSON_NUMBER, LAST_LESSON_NUMBER).boxed()
        .collect(Collectors.toMap(
            lessonNum -> keyGenerator(lessonNum, offset, order),
            lessonNum -> valueGenerator(lessonNum, offset, order)));
  }

  private int keyGenerator(int lessonNumber, int offset, Order order) {
    return switch (order) {
      case FORWARD -> lessonNumber;
      case REVERSE -> lessonNumber + offset; };
  }

  private int valueGenerator(int lessonNumber, int offset, Order order) {
    return switch (order) {
      case FORWARD -> lessonNumber + offset;
      case REVERSE -> lessonNumber; };
  }

  private enum Order {
    FORWARD,
    REVERSE
  }
}
