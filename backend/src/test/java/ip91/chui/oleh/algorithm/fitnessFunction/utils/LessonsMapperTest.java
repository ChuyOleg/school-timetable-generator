package ip91.chui.oleh.algorithm.fitnessFunction.utils;

import static org.assertj.core.api.Assertions.assertThat;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

final class LessonsMapperTest {

  private static final int MONDAY_LESSONS_COUNT = 3;
  private static final int TUESDAY_LESSONS_COUNT = 5;

  @Test
  void shouldProperlyMapToLessonPerDayByTeacher() {
    TeacherDto teacher1 = new TeacherDto(1L, "One", null, 20, null);
    TeacherDto teacher2 = new TeacherDto(2L, "Two", null, 20, null);

    List<LessonDto> teacher1MondayLessons = generateLessons(DayOfWeek.MONDAY, teacher1, MONDAY_LESSONS_COUNT);
    List<LessonDto> teacher1TuesdayLessons = generateLessons(DayOfWeek.TUESDAY, teacher1, TUESDAY_LESSONS_COUNT);

    List<LessonDto> teacher2MondayLessons = generateLessons(DayOfWeek.MONDAY, teacher2, MONDAY_LESSONS_COUNT);
    List<LessonDto> teacher2TuesdayLessons = generateLessons(DayOfWeek.TUESDAY, teacher2, TUESDAY_LESSONS_COUNT);

    List<LessonDto> lessons = Stream.of(teacher1MondayLessons, teacher1TuesdayLessons,
            teacher2MondayLessons, teacher2TuesdayLessons)
        .flatMap(List::stream).toList();

    Map<TeacherDto, Map<DayOfWeek, List<LessonDto>>> resultMap = LessonsMapper.toLessonsPerDayByTeacher(lessons);

    assertThat(resultMap).hasSize(2);
    assertThat(resultMap.get(teacher1)).hasSize(2);
    assertThat(resultMap.get(teacher2)).hasSize(2);
  }

  private List<LessonDto> generateLessons(DayOfWeek day, TeacherDto teacher, int count) {
    return IntStream.range(0, count).boxed()
        .map(lessonNum -> createLesson(day, teacher))
        .collect(Collectors.toList());
  }

  private LessonDto createLesson(DayOfWeek day, TeacherDto teacher) {
    TimeSlotDto timeslot = new TimeSlotDto(null, 1, day, 1, WeekType.BOTH);
    return new LessonDto(null, null, teacher, null, null, timeslot);
  }
}