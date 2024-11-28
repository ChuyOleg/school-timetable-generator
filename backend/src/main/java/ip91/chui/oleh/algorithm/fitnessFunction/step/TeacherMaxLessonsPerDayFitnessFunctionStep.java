package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsMapper;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeacherMaxLessonsPerDayFitnessFunctionStep implements FitnessFunctionStep {

  private final int fine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateTeacherMaxLessonsPerDayFitnessScore(context.getLessons());

    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateTeacherMaxLessonsPerDayFitnessScore(List<LessonDto> lessons) {
    var lessonsPerDayByTeacher = LessonsMapper.toLessonsPerDayByTeacher(lessons);

    return lessonsPerDayByTeacher.entrySet().stream()
        .filter(entry -> hasTeacherMaxLessonsPerDayLimit(entry.getKey()))
        .map(entry -> calculateFitnessScore(entry.getKey(), entry.getValue()))
        .mapToInt(Integer::intValue)
        .sum();
  }

  private int calculateFitnessScore(TeacherDto teacher, Map<DayOfWeek, List<LessonDto>> lessonsByDay) {
    var maxLessonsPerDayCount = teacher.getLimits().getMaxLessonsLimit().getCount();

    return lessonsByDay.values().stream()
        .filter(lessons -> lessons.size() > maxLessonsPerDayCount)
        .map(lessons -> lessons.size() - maxLessonsPerDayCount)
        .map(extraLessonsCount -> extraLessonsCount * fine)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private boolean hasTeacherMaxLessonsPerDayLimit(TeacherDto teacher) {
    return teacher.getLimits() != null && teacher.getLimits().getMaxLessonsLimit() != null;
  }
}
