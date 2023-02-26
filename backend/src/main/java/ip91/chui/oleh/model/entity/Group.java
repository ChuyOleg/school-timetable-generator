package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.LessonNumber;
import ip91.chui.oleh.model.enumeration.Shift;
import ip91.chui.oleh.model.enumeration.WeekType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.*;

@AllArgsConstructor
@Setter
@Getter
public class Group {

  private Long classId;
  private GradeNumber gradeNumber;
  private String letter;
  private Room defaultRoom;
  private List<Lesson> lessons;
  private Map<Subject, Teacher> subjectTeacherMap;
  private Shift shift;
  private int maxHoursPerWeek;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String groupString = String.format("\nGroup: %s-%s\n", this.gradeNumber.getNum(), this.letter);
    sb.append(groupString);

    // Create a map to group the lessons by day and lesson number
    Map<DayOfWeek, Map<LessonNumber, Lesson>> lessonsByDayAndNumberEvenWeek = new HashMap<>();
    Map<DayOfWeek, Map<LessonNumber, Lesson>> lessonsByDayAndNumberOddWeek = new HashMap<>();

    for (Lesson lesson : this.lessons) {
      DayOfWeek day = lesson.getTimeSlot().getDay();
      LessonNumber lessonNumber = lesson.getTimeSlot().getLessonNumber();

      switch (lesson.getTimeSlot().getWeekType()) {
        case ODD -> {
          lessonsByDayAndNumberOddWeek.putIfAbsent(day, new HashMap<>());
          lessonsByDayAndNumberOddWeek.get(day).put(lessonNumber, lesson);
        }
        case EVEN -> {
          lessonsByDayAndNumberEvenWeek.putIfAbsent(day, new HashMap<>());
          lessonsByDayAndNumberEvenWeek.get(day).put(lessonNumber, lesson);
        }
        case BOTH -> {
          lessonsByDayAndNumberOddWeek.putIfAbsent(day, new HashMap<>());
          lessonsByDayAndNumberOddWeek.get(day).put(lessonNumber, lesson);
          lessonsByDayAndNumberEvenWeek.putIfAbsent(day, new HashMap<>());
          lessonsByDayAndNumberEvenWeek.get(day).put(lessonNumber, lesson);
        }
      }
    }

    // Find the maximum subject name length to determine column width
    int maxSubjectLength = lessonsByDayAndNumberEvenWeek.values().stream()
        .flatMap(map -> map.values().stream())
        .map(Lesson::getSubject)
        .map(Subject::getName)
        .mapToInt(String::length)
        .max()
        .orElse(0);

    // Create the header row with day names
    sb.append(WeekType.EVEN).append(" week \n");
    String header = String.format("%-26s | %-26s | %-26s | %-26s | %-26s | \n",
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
    sb.append(header);

    // Create the rows for each lesson number
    for (LessonNumber lessonNumber : LessonNumber.values()) {
      StringBuilder row = new StringBuilder();
      for (DayOfWeek day : DayOfWeek.values()) {
        if (day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)) continue;
        Lesson lesson = lessonsByDayAndNumberEvenWeek.getOrDefault(day, Collections.emptyMap()).get(lessonNumber);
        String subjectName = lesson == null ? "" : lesson.getSubject().getName();
        String formattedSubjectName = String.format("%-" + maxSubjectLength + "s", subjectName);
        String formattedLesson = String.format("%d. %s", lessonNumber.getNum(), formattedSubjectName);
        row.append(String.format("%-10s", formattedLesson));
        row.append(" | ");
      }
      sb.append(row.toString().stripTrailing());
      sb.append("\n");
    }

    sb.append("\n");
    sb.append(WeekType.ODD).append(" week \n");
    sb.append(header);

    for (LessonNumber lessonNumber : LessonNumber.values()) {
      StringBuilder row = new StringBuilder();
      for (DayOfWeek day : DayOfWeek.values()) {
        if (day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)) continue;
        Lesson lesson = lessonsByDayAndNumberOddWeek.getOrDefault(day, Collections.emptyMap()).get(lessonNumber);
        String subjectName = lesson == null ? "" : lesson.getSubject().getName();
        String formattedSubjectName = String.format("%-" + maxSubjectLength + "s", subjectName);
        String formattedLesson = String.format("%d. %s", lessonNumber.getNum(), formattedSubjectName);
        row.append(String.format("%-10s", formattedLesson));
        row.append(" | ");
      }
      sb.append(row.toString().stripTrailing());
      sb.append("\n");
    }

    return sb.toString();
  }

  // Only a few subjects require not default room (Physic or Chemistry labs, Foreign_Less (because of 2-subGroups), so on)

  // Algorithm:
  // class - gene. There will be small number of genes, so maybe the crossover should produce more children?
  // crossover - mix timetables of classes from parents
  // mutation - swap TimeSlot of 2 (or more) random lessons
  // Constraint:
  //     HARD: hours per week (class | teacher), more than 1 lesson at the same time (for class or teacher)
  //     MEDIUM:
  //     LOW: order of lessons (for class | teacher), teacher's desires
  //
  //     initialGeneration: teachers hours limit, lessons hours limit
  //     crossover | mutation: sameRooms, teacher has more than 1 lesson at the same time, class has more than 1 lesson at the same time
  // Shifts solves 'same room' problem

}
