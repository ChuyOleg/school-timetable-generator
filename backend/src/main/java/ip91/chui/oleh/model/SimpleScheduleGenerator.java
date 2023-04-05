//package ip91.chui.oleh.model;
//
//import ip91.chui.oleh.model.entity.*;
//import ip91.chui.oleh.model.enumeration.LessonNumber;
//import ip91.chui.oleh.model.enumeration.WeekType;
//import lombok.RequiredArgsConstructor;
//
//import java.time.DayOfWeek;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//@RequiredArgsConstructor
//public class SimpleScheduleGenerator {
//
//  private static final int WORKING_DAYS_COUNT = 5;
//
//  private final Random random;
//  private final GroupLimitsFactory groupLimitsFactory;
//
//  // TODO: consider common subjects (HANDICRAFT, PHYSICAL_CULTURE, INFORMATICS) when room have space more than for one class
//  public TimeTable generate(Set<Group> groups) {
//    groups.forEach(group -> generateForSingleClass(group, groupLimitsFactory.createDefaultGroupLimitsByGrade(group.getGradeNumber())));
//
//    return new TimeTable();
//  }
//
//  private void generateForSingleClass(Group group, GroupLimits groupLimits) {
//    List<Subject> availableSubjects = getAllSubjectsExceptHalfWithDuplicatesFromGroupLimits(groupLimits);
//    List<Subject> halfSubjects = getHalfSubjectFromGroupLimits(groupLimits);
//
//    Map<DayOfWeek, Integer> lessonCountMap = generateLessonCountPerDayMap(group.getGroupLimits().getMaxHoursPerWeek(), groupLimits);
//
//    generateLessonsForSpecificGroupSpecificDay(group, DayOfWeek.MONDAY, lessonCountMap.get(DayOfWeek.MONDAY), availableSubjects, halfSubjects);
//    generateLessonsForSpecificGroupSpecificDay(group, DayOfWeek.TUESDAY, lessonCountMap.get(DayOfWeek.TUESDAY), availableSubjects, halfSubjects);
//    generateLessonsForSpecificGroupSpecificDay(group, DayOfWeek.WEDNESDAY, lessonCountMap.get(DayOfWeek.WEDNESDAY), availableSubjects, halfSubjects);
//    generateLessonsForSpecificGroupSpecificDay(group, DayOfWeek.THURSDAY, lessonCountMap.get(DayOfWeek.THURSDAY), availableSubjects, halfSubjects);
//    generateLessonsForSpecificGroupSpecificDay(group, DayOfWeek.FRIDAY, lessonCountMap.get(DayOfWeek.FRIDAY), availableSubjects, halfSubjects);
//  }
//
//  private void generateLessonsForSpecificGroupSpecificDay(Group group, DayOfWeek day, int lessonsCount, List<Subject>
//      availableSubjects, List<Subject> halfSubjects) {
//
//    IntStream.range(0, lessonsCount)
//        .forEach(i -> {
//          random.nextInt(lessonsCount * WORKING_DAYS_COUNT);
//          if (isHalfSubjectPickTime(lessonsCount, halfSubjects.size()) || availableSubjects.isEmpty()) {
//            Subject subject_1 = halfSubjects.remove(halfSubjects.size() - 1);
//            Subject subject_2 = halfSubjects.remove(halfSubjects.size() - 1);
//
//            TimeSlot timeSlot_1 = new TimeSlot(getLessonNumberByIntValue(i+1), day, WeekType.EVEN);
//            TimeSlot timeSlot_2 = new TimeSlot(getLessonNumberByIntValue(i+1), day, WeekType.ODD);
//
//            group.getLessonSet().add(new Lesson(subject_1, timeSlot_1));
//            group.getLessonSet().add(new Lesson(subject_2, timeSlot_2));
//          } else {
//            Subject subject = pickRandomSubject(availableSubjects);
//            TimeSlot timeSlot = new TimeSlot(getLessonNumberByIntValue(i+1), day, WeekType.BOTH);
//            group.getLessonSet().add(new Lesson(subject, timeSlot));
//          }
//        });
//  }
//
//  private Subject pickRandomSubject(List<Subject> subjects) {
//    final int randomSubjectNum = random.nextInt(subjects.size());
//    return subjects.remove(randomSubjectNum);
//  }
//
//  private Map<DayOfWeek, Integer> generateLessonCountPerDayMap(int lessonsCount, GroupLimits groupLimits) {
//    Map<DayOfWeek, Integer> lessonCountByDayMap = new HashMap<>();
//    List<DayOfWeek> days = getListOfWorkingDays();
//    int meanNum;
//    int remainder;
//
//    if (groupLimits.getInterschoolCombine() != null) {
//      TimeSlot interschoolCombineInfo = groupLimits.getInterschoolCombine();
//      days.remove(interschoolCombineInfo.getDay());
//
//      int interschoolCombineDayLessonCount = interschoolCombineInfo.getLessonNumber().getNum() - 1;
//      int lessonCountExceptInterschoolCombineDay = lessonsCount - interschoolCombineDayLessonCount;
//      meanNum = lessonCountExceptInterschoolCombineDay / (WORKING_DAYS_COUNT - 1);
//      remainder = lessonCountExceptInterschoolCombineDay % (WORKING_DAYS_COUNT - 1);
//      lessonCountByDayMap.put(interschoolCombineInfo.getDay(), interschoolCombineDayLessonCount);
//    } else {
//      meanNum = lessonsCount / WORKING_DAYS_COUNT;
//      remainder = lessonsCount % WORKING_DAYS_COUNT;
//    }
//
//    days.forEach(day -> lessonCountByDayMap.put(day, meanNum));
//
//    for (int idx = 0; idx < remainder; idx++) {
//      final int randomDayNum = random.nextInt(days.size());
//      DayOfWeek randomDay = days.remove(randomDayNum);
//      lessonCountByDayMap.put(randomDay, lessonCountByDayMap.get(randomDay) + 1);
//    }
//
//    return lessonCountByDayMap;
//  }
//
//  private List<Subject> getAllSubjectsExceptHalfWithDuplicatesFromGroupLimits(GroupLimits groupLimits) {
//    return groupLimits.getSubjectHoursMap().entrySet().stream()
//        .flatMap((Map.Entry<Subject, Double> entry) ->
//            IntStream.range(0, (int) Math.floor(entry.getValue())).mapToObj((i) -> entry.getKey()))
//        .collect(Collectors.toList());
//  }
//
//  private List<Subject> getHalfSubjectFromGroupLimits(GroupLimits groupLimits) {
//    return groupLimits.getSubjectHoursMap().entrySet().stream()
//        .filter((Map.Entry<Subject, Double> entry) -> entry.getValue() != Math.floor(entry.getValue()))
//        .map(Map.Entry::getKey)
//        .collect(Collectors.toList());
//  }
//
//  private boolean isHalfSubjectPickTime(int lessonCountPerDay, int halfSubjectCount) {
//    return random.nextInt(lessonCountPerDay * WORKING_DAYS_COUNT) < (halfSubjectCount / 2);
//  }
//
//  private LessonNumber getLessonNumberByIntValue(int number) {
//    return switch (number) {
//      case 1 -> LessonNumber.FIRST;
//      case 2 -> LessonNumber.SECOND;
//      case 3 -> LessonNumber.THIRD;
//      case 4 -> LessonNumber.FOURTH;
//      case 5 -> LessonNumber.FIFTH;
//      case 6 -> LessonNumber.SIXTH;
//      case 7 -> LessonNumber.SEVENTH;
//      case 8 -> LessonNumber.EIGHTH;
//      default -> throw new RuntimeException("Invalid lesson number");
//    };
//  }
//
//  private List<DayOfWeek> getListOfWorkingDays() {
//    return Stream.of(
//        DayOfWeek.MONDAY,
//        DayOfWeek.TUESDAY,
//        DayOfWeek.WEDNESDAY,
//        DayOfWeek.THURSDAY,
//        DayOfWeek.FRIDAY
//    ).collect(Collectors.toList());
//  }
//
//}
