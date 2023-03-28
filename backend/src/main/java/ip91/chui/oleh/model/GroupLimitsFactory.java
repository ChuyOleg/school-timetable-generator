package ip91.chui.oleh.model;

import ip91.chui.oleh.model.entity.GroupLimits;
import ip91.chui.oleh.model.entity.Subject;
import ip91.chui.oleh.model.entity.TimeSlot;
import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.LessonNumber;
import ip91.chui.oleh.model.enumeration.WeekType;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class GroupLimitsFactory {

  public static final String SUBJECTS_1_4_CLASSES = "CLASS_TEACHER_1_4_CLASSES";
  public static final String UKR_LANGUAGE_WRITING = "UKR_LANGUAGE_WRITING";
  public static final String UKR_LANGUAGE_READING = "UKR_LANGUAGE_READING";
  public static final String ENGLISH = "ENGLISH";
  public static final String MATHEMATICS = "MATHEMATICS";
  public static final String I_EXPLORE_THE_WORLD = "I_EXPLORE_THE_WORLD";
  public static final String DESIGN_AND_TECHNOLOGIES = "DESIGN_AND_TECHNOLOGIES";
  public static final String ART = "ART";
  public static final String PHYSICAL_CULTURE = "PHYSICAL_CULTURE";
  public static final String INFORMATICS = "INFORMATICS";
  public static final String UKR_LANGUAGE = "UKR_LANGUAGE";
  public static final String UKR_LITERATURE = "UKR_LITERATURE";
  public static final String GERMAN = "GERMAN";
  public static final String FOREIGN_LITERATURE = "FOREIGN_LITERATURE";
  public static final String NATURAL_SCIENCE = "NATURAL_SCIENCE";
  public static final String HISTORY = "HISTORY";
  public static final String HANDICRAFT = "HANDICRAFT";
  public static final String MUSIC = "MUSIC";
  public static final String HEALTH_BASICS = "HEALTH_BASICS";
  public static final String GEOGRAPHY = "GEOGRAPHY";
  public static final String PHYSICS = "PHYSICS";
  public static final String CHEMISTRY = "CHEMISTRY";
  public static final String HISTORY_OF_UKRAINE = "HISTORY_OF_UKRAINE";
  public static final String WORLD_HISTORY = "WORLD_HISTORY";
  public static final String BIOLOGY = "BIOLOGY";
  public static final String BASICS_OF_JURISPRUDENCE = "BASICS_OF_JURISPRUDENCE";
  public static final String PROTECTION_OF_MOTHERLAND = "PROTECTION_OF_MOTHERLAND";

  public GroupLimits createDefaultGroupLimitsByGrade(GradeNumber gradeNumber) {
    return switch (gradeNumber) {
      case FIRST -> createFirstGradeGroupLimits();
      case SECOND -> createSecondGradeGroupLimits();
      case THIRD -> createThirdGradeGroupLimits();
      case FOURTH -> createFourthGradeGroupLimits();
      case FIFTH -> createFifthGradeGroupLimits();
      case SIXTH -> createSixthGradeGroupLimits();
      case SEVENTH -> createSeventhGradeGroupLimits();
      case EIGHTH -> createEighthGradeGroupLimits();
      case NINTH -> createNinthGradeGroupLimits();
      case TENTH -> createTenthGradeGroupLimits();
      case ELEVENTH -> createEleventhGradeGroupLimits();
    };
  }

  private GroupLimits createFirstGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_WRITING), 4.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_READING), 3.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 2.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(I_EXPLORE_THE_WORLD), 3.0);
    groupMaxSubjectCountMap.put(new Subject(DESIGN_AND_TECHNOLOGIES), 1.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 2.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createSecondGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_WRITING), 4.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_READING), 3.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(I_EXPLORE_THE_WORLD), 3.0);
    groupMaxSubjectCountMap.put(new Subject(DESIGN_AND_TECHNOLOGIES), 1.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 2.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createThirdGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_WRITING), 4.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_READING), 3.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 5.0);
    groupMaxSubjectCountMap.put(new Subject(I_EXPLORE_THE_WORLD), 3.0);
    groupMaxSubjectCountMap.put(new Subject(DESIGN_AND_TECHNOLOGIES), 1.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 2.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createFourthGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_WRITING), 4.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE_READING), 3.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 5.0);
    groupMaxSubjectCountMap.put(new Subject(I_EXPLORE_THE_WORLD), 3.0);
    groupMaxSubjectCountMap.put(new Subject(DESIGN_AND_TECHNOLOGIES), 1.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 2.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createFifthGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(NATURAL_SCIENCE), 1.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HANDICRAFT), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 0.5);
    groupMaxSubjectCountMap.put(new Subject(MUSIC), 0.5);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(HEALTH_BASICS), 1.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createSixthGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 5.0);
    groupMaxSubjectCountMap.put(new Subject(NATURAL_SCIENCE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HANDICRAFT), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 0.5);
    groupMaxSubjectCountMap.put(new Subject(MUSIC), 0.5);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(GEOGRAPHY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HEALTH_BASICS), 1.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createSeventhGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 1.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(CHEMISTRY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY_OF_UKRAINE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(WORLD_HISTORY), 1.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HANDICRAFT), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 0.5);
    groupMaxSubjectCountMap.put(new Subject(MUSIC), 0.5);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(BIOLOGY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GEOGRAPHY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HEALTH_BASICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(BASICS_OF_JURISPRUDENCE), 1.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createEighthGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 1.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(CHEMISTRY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY_OF_UKRAINE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(WORLD_HISTORY), 1.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HANDICRAFT), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ART), 0.5);
    groupMaxSubjectCountMap.put(new Subject(MUSIC), 0.5);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(BIOLOGY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GEOGRAPHY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HEALTH_BASICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(BASICS_OF_JURISPRUDENCE), 1.0);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);

    return groupLimits;
  }

  private GroupLimits createNinthGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 1.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 5.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(CHEMISTRY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY_OF_UKRAINE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(WORLD_HISTORY), 1.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(BIOLOGY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GEOGRAPHY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HEALTH_BASICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(BASICS_OF_JURISPRUDENCE), 1.0);

    final TimeSlot interschoolCombine = new TimeSlot(LessonNumber.FIFTH, DayOfWeek.MONDAY, WeekType.BOTH);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);
    groupLimits.setInterschoolCombine(interschoolCombine);

    return groupLimits;
  }

  private GroupLimits createTenthGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 1.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(CHEMISTRY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY_OF_UKRAINE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(BIOLOGY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GEOGRAPHY), 1.0);
    groupMaxSubjectCountMap.put(new Subject(PROTECTION_OF_MOTHERLAND), 2.0);

    final TimeSlot interschoolCombine = new TimeSlot(LessonNumber.FIFTH, DayOfWeek.TUESDAY, WeekType.BOTH);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);
    groupLimits.setInterschoolCombine(interschoolCombine);

    return groupLimits;
  }

  private GroupLimits createEleventhGradeGroupLimits() {
    final Map<Subject, Double> groupMaxSubjectCountMap = new HashMap<>();
    groupMaxSubjectCountMap.put(new Subject(UKR_LANGUAGE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(UKR_LITERATURE), 2.0);
    groupMaxSubjectCountMap.put(new Subject(ENGLISH), 3.0);
    groupMaxSubjectCountMap.put(new Subject(GERMAN), 2.0);
    groupMaxSubjectCountMap.put(new Subject(FOREIGN_LITERATURE), 1.0);
    groupMaxSubjectCountMap.put(new Subject(MATHEMATICS), 4.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICS), 2.0);
    groupMaxSubjectCountMap.put(new Subject(CHEMISTRY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(HISTORY_OF_UKRAINE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(INFORMATICS), 1.0);
    groupMaxSubjectCountMap.put(new Subject(PHYSICAL_CULTURE), 3.0);
    groupMaxSubjectCountMap.put(new Subject(BIOLOGY), 2.0);
    groupMaxSubjectCountMap.put(new Subject(GEOGRAPHY), 1.0);
    groupMaxSubjectCountMap.put(new Subject(PROTECTION_OF_MOTHERLAND), 2.0);

    final TimeSlot interschoolCombine = new TimeSlot(LessonNumber.FIFTH, DayOfWeek.THURSDAY, WeekType.BOTH);

    GroupLimits groupLimits = new GroupLimits();
    groupLimits.setSubjectHoursMap(groupMaxSubjectCountMap);
    groupLimits.setInterschoolCombine(interschoolCombine);

    return groupLimits;
  }

}
