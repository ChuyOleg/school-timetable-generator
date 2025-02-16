package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsMapper;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.enumeration.ImportanceLevel;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

// ToDo: 13/11/24 refactor
// ToDO: 16/11/24 check how it handles shift_2 comparison.
@RequiredArgsConstructor
public class TeacherLessonsOrderFitnessFunction implements FitnessFunctionStep {

  private final int mediumFine;
  private final int highFine;

  @Override
  public void calculate(Individual individual, FitnessFunctionContext context) {
    int fitnessScore = calculateTeacherLessonsOrderFitnessScore(context.getLessons());

    context.increaseFitnessScore(fitnessScore);
  }

  private int calculateTeacherLessonsOrderFitnessScore(List<LessonDto> lessons) {
    var lessonsByTeacherByDay = LessonsMapper.toLessonsPerDayByTeacher(lessons);

    return lessonsByTeacherByDay.keySet().stream()
        .filter(this::hasTeacherLessonsOrderLimit)
        .map(teacher -> this.calculateForWeekForTeacher(teacher, lessonsByTeacherByDay.get(teacher)))
        .mapToInt(Integer::intValue)
        .sum();
  }

  private int calculateForWeekForTeacher(TeacherDto teacher, Map<DayOfWeek, List<LessonDto>> lessonsByDays) {
    var lessonsOrderLimit = teacher.getLimits().getLessonsOrderLimit();

    if (lessonsOrderLimit.getImportanceLevel().equals(ImportanceLevel.LOW)) {
      return 0;
    }

    return lessonsByDays.keySet().stream()
        .map(day -> this.calculateForSpecificDayForTeacher(teacher, lessonsByDays.get(day)))
        .mapToInt(Integer::intValue)
        .sum();
  }

  private boolean hasTeacherLessonsOrderLimit(TeacherDto teacher) {
    return teacher.getLimits() != null && teacher.getLimits().getLessonsOrderLimit() != null;
  }

  private int calculateForSpecificDayForTeacher(TeacherDto teacher, List<LessonDto> lessons) {
    Set<Integer> teacherLessonsNumber = lessons.stream()
        .map(lesson -> lesson.getTimeSlotDto().getLessonNumber())
        .collect(Collectors.toSet());

    final AtomicInteger previousLessonNumber = new AtomicInteger(0);
    final AtomicInteger score = new AtomicInteger(0);

    teacherLessonsNumber.forEach(lessonNumber -> {
      if (previousLessonNumber.get() > 0 && lessonNumber - previousLessonNumber.get() > 1) {
        score.set(score.intValue() + (lessonNumber - previousLessonNumber.get() - 1));
      }

      previousLessonNumber.set(lessonNumber);
    });

    return score.get() * getFine(teacher);
  }

  private int getFine(TeacherDto teacher) {
    return switch (teacher.getLimits().getLessonsOrderLimit().getImportanceLevel()) {
      case MEDIUM -> mediumFine;
      case HIGH -> highFine;
      default -> throw new IllegalArgumentException("Non-expected importance level."); };
  }
}
