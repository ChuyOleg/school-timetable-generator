package ip91.chui.oleh;

import ip91.chui.oleh.model.GroupLimitsFactory;
import ip91.chui.oleh.model.SimpleFitnessFunction;
import ip91.chui.oleh.model.SimpleScheduleGenerator;
import ip91.chui.oleh.model.entity.*;
import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.Shift;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static ip91.chui.oleh.model.GroupLimitsFactory.*;
import static ip91.chui.oleh.model.GroupLimitsFactory.PROTECTION_OF_MOTHERLAND;

@RequiredArgsConstructor
public class Runner {

  private final GroupLimitsFactory groupLimitsFactory;
  private final SimpleScheduleGenerator generator;
  private final SimpleFitnessFunction fitnessFunction;

  public void run() {
    Map<String, Subject> subjectByNameMap = createSubjectsByNameMap();
    List<Teacher> teachers = createTeachers(subjectByNameMap);
    List<Group> groups = createGroups(subjectByNameMap, teachers);

    TimeTable timeTable = generator.generate(groups);

    timeTable.getGroups().forEach(System.out::println);
  }

  private List<Group> createGroups(Map<String, Subject> subjectByNameMap, List<Teacher> teachers) {
    List<Group> groups = new ArrayList<>();

    Long id_1 = 1L;
    Long id_2 = 2L;
    Long id_3 = 3L;

    String letter_A = "A";

    Room room_1 = new Room(1L, 1, 30);
    Room room_2 = new Room(2L, 2, 32);
    Room room_3 = new Room(3L, 3, 28);

    Map<Subject, Teacher> subjectTeacherMap_1 = new HashMap<>();
    subjectTeacherMap_1.put(subjectByNameMap.get(UKR_LANGUAGE_WRITING), teachers.get(0));
    subjectTeacherMap_1.put(subjectByNameMap.get(UKR_LANGUAGE_READING), teachers.get(0));
    subjectTeacherMap_1.put(subjectByNameMap.get(ENGLISH), teachers.get(1));
    subjectTeacherMap_1.put(subjectByNameMap.get(MATHEMATICS), teachers.get(0));
    subjectTeacherMap_1.put(subjectByNameMap.get(I_EXPLORE_THE_WORLD), teachers.get(0));
    subjectTeacherMap_1.put(subjectByNameMap.get(DESIGN_AND_TECHNOLOGIES), teachers.get(0));
    subjectTeacherMap_1.put(subjectByNameMap.get(INFORMATICS), teachers.get(6));
    subjectTeacherMap_1.put(subjectByNameMap.get(ART), teachers.get(0));
    subjectTeacherMap_1.put(subjectByNameMap.get(PHYSICAL_CULTURE), teachers.get(5));

    Map<Subject, Teacher> subjectTeacherMap_2 = new HashMap<>();
    subjectTeacherMap_2.put(subjectByNameMap.get(UKR_LANGUAGE), teachers.get(7));
    subjectTeacherMap_2.put(subjectByNameMap.get(UKR_LITERATURE), teachers.get(8));
    subjectTeacherMap_2.put(subjectByNameMap.get(ENGLISH), teachers.get(1));
    subjectTeacherMap_2.put(subjectByNameMap.get(GERMAN), teachers.get(9));
    subjectTeacherMap_2.put(subjectByNameMap.get(FOREIGN_LITERATURE), teachers.get(10));
    subjectTeacherMap_2.put(subjectByNameMap.get(MATHEMATICS), teachers.get(2));
    subjectTeacherMap_2.put(subjectByNameMap.get(PHYSICS), teachers.get(17));
    subjectTeacherMap_2.put(subjectByNameMap.get(CHEMISTRY), teachers.get(18));
    subjectTeacherMap_2.put(subjectByNameMap.get(HISTORY_OF_UKRAINE), teachers.get(19));
    subjectTeacherMap_2.put(subjectByNameMap.get(WORLD_HISTORY), teachers.get(20));
    subjectTeacherMap_2.put(subjectByNameMap.get(INFORMATICS), teachers.get(6));
    subjectTeacherMap_2.put(subjectByNameMap.get(HANDICRAFT), teachers.get(13));
    subjectTeacherMap_2.put(subjectByNameMap.get(ART), teachers.get(4));
    subjectTeacherMap_2.put(subjectByNameMap.get(MUSIC), teachers.get(14));
    subjectTeacherMap_2.put(subjectByNameMap.get(PHYSICAL_CULTURE), teachers.get(5));
    subjectTeacherMap_2.put(subjectByNameMap.get(BIOLOGY), teachers.get(21));
    subjectTeacherMap_2.put(subjectByNameMap.get(GEOGRAPHY), teachers.get(16));
    subjectTeacherMap_2.put(subjectByNameMap.get(HEALTH_BASICS), teachers.get(15));
    subjectTeacherMap_2.put(subjectByNameMap.get(BASICS_OF_JURISPRUDENCE), teachers.get(22));

    Map<Subject, Teacher> subjectTeacherMap_3 = new HashMap<>();
    subjectTeacherMap_3.put(subjectByNameMap.get(UKR_LANGUAGE), teachers.get(7));
    subjectTeacherMap_3.put(subjectByNameMap.get(UKR_LITERATURE), teachers.get(8));
    subjectTeacherMap_3.put(subjectByNameMap.get(ENGLISH), teachers.get(1));
    subjectTeacherMap_3.put(subjectByNameMap.get(GERMAN), teachers.get(9));
    subjectTeacherMap_3.put(subjectByNameMap.get(FOREIGN_LITERATURE), teachers.get(10));
    subjectTeacherMap_3.put(subjectByNameMap.get(MATHEMATICS), teachers.get(2));
    subjectTeacherMap_3.put(subjectByNameMap.get(PHYSICS), teachers.get(17));
    subjectTeacherMap_3.put(subjectByNameMap.get(CHEMISTRY), teachers.get(18));
    subjectTeacherMap_3.put(subjectByNameMap.get(HISTORY_OF_UKRAINE), teachers.get(19));
    subjectTeacherMap_3.put(subjectByNameMap.get(INFORMATICS), teachers.get(6));
    subjectTeacherMap_3.put(subjectByNameMap.get(PHYSICAL_CULTURE), teachers.get(5));
    subjectTeacherMap_3.put(subjectByNameMap.get(BIOLOGY), teachers.get(21));
    subjectTeacherMap_3.put(subjectByNameMap.get(GEOGRAPHY), teachers.get(16));
    subjectTeacherMap_3.put(subjectByNameMap.get(PROTECTION_OF_MOTHERLAND), teachers.get(23));


    groups.add(new Group(id_1, GradeNumber.THIRD, letter_A, room_1, new ArrayList<>(), subjectTeacherMap_1, Shift.SECOND, 25));
    groups.add(new Group(id_2, GradeNumber.SEVENTH, letter_A, room_2, new ArrayList<>(), subjectTeacherMap_2, Shift.FIRST, 35));
    groups.add(new Group(id_3, GradeNumber.TENTH, letter_A, room_3, new ArrayList<>(), subjectTeacherMap_3, Shift.FIRST, 31));

    return groups;
  }

  // TODO: think that for 1-4 classes their class teacher conducts almost all lessons
  private List<Teacher> createTeachers(Map<String, Subject> subjectMap) {
    List<Teacher> teachers = new ArrayList<>();

    teachers.add(new Teacher("Kate Miles", Set.of(subjectMap.get(SUBJECTS_1_4_CLASSES)), 25));
    teachers.add(new Teacher("John Week", Set.of(subjectMap.get(ENGLISH)), 14));
    teachers.add(new Teacher("Denton Rios", Set.of(subjectMap.get(MATHEMATICS)), 12));
    teachers.add(new Teacher("Earl Tate", Set.of(subjectMap.get(I_EXPLORE_THE_WORLD)), 6));
    teachers.add(new Teacher("Dudley Harper", Set.of(subjectMap.get(ART)), 13));
    teachers.add(new Teacher("Halle Lawson", Set.of(subjectMap.get(PHYSICAL_CULTURE)), 15));
    teachers.add(new Teacher("Belle Kemp", Set.of(subjectMap.get(INFORMATICS)), 10));
    teachers.add(new Teacher("Winona Stephenson", Set.of(subjectMap.get(UKR_LANGUAGE)), 11));
    teachers.add(new Teacher("Cade Warren", Set.of(subjectMap.get(UKR_LITERATURE)), 14));
    teachers.add(new Teacher("Nydia Reid", Set.of(subjectMap.get(GERMAN)), 12));
    teachers.add(new Teacher("Linden Davison", Set.of(subjectMap.get(FOREIGN_LITERATURE)), 16));
    teachers.add(new Teacher("Harley Gill", Set.of(subjectMap.get(NATURAL_SCIENCE)), 12));
    teachers.add(new Teacher("Rebecca Hargraves", Set.of(subjectMap.get(HISTORY)), 8));
    teachers.add(new Teacher("Michael Lawrence", Set.of(subjectMap.get(HANDICRAFT)), 12));
    teachers.add(new Teacher("Ash Marshall", Set.of(subjectMap.get(MUSIC)), 16));
    teachers.add(new Teacher("Russ Burns", Set.of(subjectMap.get(HEALTH_BASICS)), 17));
    teachers.add(new Teacher("Ferris Santos", Set.of(subjectMap.get(GEOGRAPHY)), 15));
    teachers.add(new Teacher("Mona Alvarez", Set.of(subjectMap.get(PHYSICS)), 13));
    teachers.add(new Teacher("Hazel Hersey", Set.of(subjectMap.get(CHEMISTRY)), 14));
    teachers.add(new Teacher("Melissa Dixon", Set.of(subjectMap.get(HISTORY_OF_UKRAINE)), 14));
    teachers.add(new Teacher("Robin Montgomery", Set.of(subjectMap.get(WORLD_HISTORY)), 14));
    teachers.add(new Teacher("Maxwell Presley", Set.of(subjectMap.get(BIOLOGY)), 12));
    teachers.add(new Teacher("Lesley Jenning", Set.of(subjectMap.get(BASICS_OF_JURISPRUDENCE)), 16));
    teachers.add(new Teacher("Alina Daniels", Set.of(subjectMap.get(PROTECTION_OF_MOTHERLAND)), 15));

    return teachers;
  }

  private Map<String, Subject> createSubjectsByNameMap() {
    Map<String, Subject> subjectByNameMap = new HashMap<>();

    subjectByNameMap.put(SUBJECTS_1_4_CLASSES, new Subject(SUBJECTS_1_4_CLASSES));
    subjectByNameMap.put(UKR_LANGUAGE_WRITING, new Subject(UKR_LANGUAGE_WRITING));
    subjectByNameMap.put(UKR_LANGUAGE_READING, new Subject(UKR_LANGUAGE_READING));
    subjectByNameMap.put(ENGLISH, new Subject(ENGLISH));
    subjectByNameMap.put(MATHEMATICS, new Subject(MATHEMATICS));
    subjectByNameMap.put(I_EXPLORE_THE_WORLD, new Subject(I_EXPLORE_THE_WORLD));
    subjectByNameMap.put(DESIGN_AND_TECHNOLOGIES, new Subject(DESIGN_AND_TECHNOLOGIES));
    subjectByNameMap.put(ART, new Subject(ART));
    subjectByNameMap.put(PHYSICAL_CULTURE, new Subject(PHYSICAL_CULTURE));
    subjectByNameMap.put(INFORMATICS, new Subject(INFORMATICS));
    subjectByNameMap.put(UKR_LANGUAGE, new Subject(UKR_LANGUAGE));
    subjectByNameMap.put(UKR_LITERATURE, new Subject(UKR_LITERATURE));
    subjectByNameMap.put(GERMAN, new Subject(GERMAN));
    subjectByNameMap.put(FOREIGN_LITERATURE, new Subject(FOREIGN_LITERATURE));
    subjectByNameMap.put(NATURAL_SCIENCE, new Subject(NATURAL_SCIENCE));
    subjectByNameMap.put(HISTORY, new Subject(HISTORY));
    subjectByNameMap.put(HANDICRAFT, new Subject(HANDICRAFT));
    subjectByNameMap.put(MUSIC, new Subject(MUSIC));
    subjectByNameMap.put(HEALTH_BASICS, new Subject(HEALTH_BASICS));
    subjectByNameMap.put(GEOGRAPHY, new Subject(GEOGRAPHY));
    subjectByNameMap.put(PHYSICS, new Subject(PHYSICS));
    subjectByNameMap.put(CHEMISTRY, new Subject(CHEMISTRY));
    subjectByNameMap.put(HISTORY_OF_UKRAINE, new Subject(HISTORY_OF_UKRAINE));
    subjectByNameMap.put(WORLD_HISTORY, new Subject(WORLD_HISTORY));
    subjectByNameMap.put(BIOLOGY, new Subject(BIOLOGY));
    subjectByNameMap.put(BASICS_OF_JURISPRUDENCE, new Subject(BASICS_OF_JURISPRUDENCE));
    subjectByNameMap.put(PROTECTION_OF_MOTHERLAND, new Subject(PROTECTION_OF_MOTHERLAND));

    return subjectByNameMap;
  }

}
