package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

// ToDo: 17/11/24 artificial mutation can be easily used here
@RequiredArgsConstructor
public class TeacherFreeDaysFitnessFunctionStep implements FitnessFunctionStep {

  private final int fine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateTeacherFreeDaysFitnessScore(context.getLessons());

    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateTeacherFreeDaysFitnessScore(List<LessonDto> lessons) {
    long violationsCount = lessons.stream()
        .filter(this::hasTeacherFreeDayLimits)
        .filter(this::isViolation)
        .count();

    return (int) violationsCount * fine;
  }

  private boolean hasTeacherFreeDayLimits(LessonDto lesson) {
    var limits = lesson.getTeacherDto().getLimits();

    return limits != null
        && limits.getFreeDayLimits() != null
        && !limits.getFreeDayLimits().isEmpty();
  }

  private boolean isViolation(LessonDto lesson) {
    var freeDayLimits = lesson.getTeacherDto().getLimits().getFreeDayLimits();
    var lessonDay = lesson.getTimeSlotDto().getDay();

    return freeDayLimits.stream().anyMatch(freeDayLimit -> freeDayLimit.getDay().equals(lessonDay));
  }
}
