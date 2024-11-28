package ip91.chui.oleh.algorithm.fitnessFunction.utils;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

final public class LessonsMapper {

  private LessonsMapper() {}

  public static Map<TeacherDto, Map<TimeSlotDto, Integer>> toLessonPerTimeslotByTeacher(
      List<LessonDto> lessons) {

    return lessons.stream()
        .collect(Collectors.groupingBy(
            LessonDto::getTeacherDto,
            Collectors.groupingBy(
                LessonDto::getTimeSlotDto,
                Collectors.summingInt(lesson -> 1)) ));
  }

  public static Map<TeacherDto, Map<DayOfWeek, List<LessonDto>>> toLessonsPerDayByTeacher(
      List<LessonDto> lessons) {

    return lessons.stream()
        .collect(Collectors.groupingBy(
            LessonDto::getTeacherDto,
            Collectors.groupingBy(lesson -> lesson.getTimeSlotDto().getDay()) ));
  }

  public static Map<RoomDto, Map<TimeSlotDto, Integer>> toLessonPerTimeslotByRoom(
      List<LessonDto> lessons) {

    return lessons.stream()
        .filter(lesson -> Objects.nonNull(lesson.getRoomDto()))
        .collect(Collectors.groupingBy(
            LessonDto::getRoomDto,
            Collectors.groupingBy(
                LessonDto::getTimeSlotDto,
                Collectors.summingInt(lesson -> 1)) ));
  }
}
