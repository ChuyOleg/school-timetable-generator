package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsMapper;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.dto.teacher.limit.DesiredPeriodLimitDto;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeacherDesiredPeriodsFitnessFunctionStep implements FitnessFunctionStep {

  private final int fine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateTeacherDesiredPeriodsFitnessScore(context.getLessons());

    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateTeacherDesiredPeriodsFitnessScore(List<LessonDto> lessons) {
    var lessonsByTeacherByDay = LessonsMapper.toLessonsPerDayByTeacher(lessons);

    return lessonsByTeacherByDay.entrySet().stream()
        .filter(entry -> hasTeacherDesiredPeriodsLimits(entry.getKey()))
        .map(entry -> calculateFitnessScore(entry.getKey(), entry.getValue()))
        .mapToInt(Integer::intValue)
        .sum();
  }

  private boolean hasTeacherDesiredPeriodsLimits(TeacherDto teacher) {
    var limits = teacher.getLimits();

    return limits != null
        && limits.getDesiredPeriodLimits() != null
        && !limits.getDesiredPeriodLimits().isEmpty();
  }

//    ToDo: 12/11/24 clarify. Can teacher have multiple DesiredPeriods for one Day?
//     Currently, this behavior is not handled. So, only 1 DesiredPeriod is expected for one Day for now.
  private int calculateFitnessScore(TeacherDto teacher, Map<DayOfWeek, List<LessonDto>> lessonsByDay) {
    Set<DesiredPeriodLimitDto> desiredPeriodLimits = teacher.getLimits().getDesiredPeriodLimits();

    return desiredPeriodLimits.stream()
        .filter(limit -> Objects.nonNull(lessonsByDay.get(limit.getDay())))
        .map(limit -> (int) lessonsByDay.get(limit.getDay()).stream()
            .filter(lesson -> isLessonOutOfDesiredPeriod(lesson, limit))
            .count())
        .mapToInt(Integer::intValue)
        .map(violationsCount -> violationsCount * fine)
        .sum();
  }

  private boolean isLessonOutOfDesiredPeriod(LessonDto lesson, DesiredPeriodLimitDto limit) {
    return lesson.getTimeSlotDto().getLessonNumber() < limit.getLessonFrom()
        || lesson.getTimeSlotDto().getLessonNumber() > limit.getLessonTo();
  }

  public record Result(List<LessonDto> problematicLessons) {};
}
