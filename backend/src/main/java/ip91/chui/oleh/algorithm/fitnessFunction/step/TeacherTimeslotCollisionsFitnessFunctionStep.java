package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsMapper;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TeacherTimeslotCollisionsFitnessFunctionStep implements FitnessFunctionStep {


  // todo: 01/10/24 teacher can have more than 1 lesson at specific conditions:
  //  e.g: teacher who drives subgroup can have subgroups from different groups at the same lesson.
  //    e.g: labor work or physical culture teachers (Biluk Bohdan explained this moment)

  private final int teacherMaxLessonsAtSameTimeLimit;
  private final int teacherMaxLessonsAtSameTimeFine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateInner(context.getLessons());
    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateInner(List<LessonDto> lessons) {
    Map<TeacherDto, Map<TimeSlotDto, Integer>> lessonsCountPerTimeslotPerTeacher =
        LessonsMapper.toLessonPerTimeslotByTeacher(lessons);

    return lessonsCountPerTimeslotPerTeacher.values().stream()
        .flatMap(lessonsCountPerTimeslot -> lessonsCountPerTimeslot.values().stream())
        .filter(lessonsCount -> lessonsCount > teacherMaxLessonsAtSameTimeLimit)
        .map(violationsCount -> violationsCount * teacherMaxLessonsAtSameTimeFine)
        .mapToInt(Integer::intValue)
        .sum();
  }
}
