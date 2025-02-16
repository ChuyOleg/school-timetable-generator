package ip91.chui.oleh.algorithm.populationGenerator;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import ip91.chui.oleh.algorithm.util.holder.RoomsHolder;
import ip91.chui.oleh.algorithm.util.holder.TeacherHolder;
import ip91.chui.oleh.algorithm.util.holder.TimeSlotsHolder;
import ip91.chui.oleh.exception.SubjectProcessingException;
import ip91.chui.oleh.model.dto.*;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.enumeration.WeekType;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TimetablePopulationGenerator implements PopulationGenerator {

  private static final String CANT_FIND_SUBJECT_LIMITS_ERROR = "Can't find SubjectLimits from GroupLimits by Subject name";
  private static final String HALF_SUBJECT_CANT_HAVE_SUBGROUPS_ERROR = "Half subject can't have subgroups";
  private static final int WORKING_DAYS_COUNT = 5;

  private final AuthenticationService authService;
  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;
  private final FitnessFunction fitnessFunction;
  private final TimeSlotsHolder timeSlotsHolder;
  private final TeacherHolder teacherHolder;
  private final RoomsHolder roomsHolder;
  private final Random random;

  @Override
  public Population generate(int populationSize) {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    List<GroupDto> groups = fetchGroupsFromDbByUserId(user.getId());

    List<Individual> individuals = new ArrayList<>(populationSize);

    for (int individualNum = 0; individualNum < populationSize; individualNum++) {
      Object[] chromosome = groups
          .stream()
          .map(group -> GroupDto
              .builder()
              .id(group.getId())
              .gradeNumber(group.getGradeNumber())
              .shift(group.getShift())
              .lessons(generateForSingleGroup(group))
              .build()
          )
          .toArray();

      Individual individual = new Individual(chromosome);
      individual.setFitness(fitnessFunction.calculate(individual));
      individuals.add(individual);
    }

    return new Population(individuals);
  }

  private Set<LessonDto> generateForSingleGroup(GroupDto group) {
    List<SubjectDto> availableSubjects = getAllSubjectsExceptHalfWithDuplicatesFromGroupLimits(group.getGroupLimitsDto());
    List<SubjectDto> halfSubjects = getHalfSubjectFromGroupLimits(group.getGroupLimitsDto());
    Map<DayOfWeek, Integer> hoursPerDayMap = generateHoursPerDayMap(group.getGroupLimitsDto());

    return getListOfWorkingDays()
        .stream()
        .flatMap(day -> generateLessonsForSpecificGroupSpecificDay(
            day, hoursPerDayMap.get(day), availableSubjects, halfSubjects, group).stream()
        )
        .collect(Collectors.toSet());
  }

  private Set<LessonDto> generateLessonsForSpecificGroupSpecificDay(
      DayOfWeek day, int hours, List<SubjectDto> availableSubjects, List<SubjectDto> halfSubjects, GroupDto group
  ) {

    Set<LessonDto> lessons = new HashSet<>();

    IntStream.range(0, hours).forEach(i -> {
      final int lessonNumber = i + 1;

      if (isHalfSubjectPickTime(hours, halfSubjects.size()) || availableSubjects.isEmpty()) {
        addHalfSubjectToLessons(WeekType.EVEN, day, lessonNumber, halfSubjects, group, lessons);
        addHalfSubjectToLessons(WeekType.ODD, day, lessonNumber, halfSubjects, group, lessons);
      } else {
        addSubjectToLessons(day, lessonNumber, availableSubjects, group, lessons);
      }
    });

    return lessons;
  }

  private void addSubjectToLessons(
      DayOfWeek day, int lessonNumber, List<SubjectDto> availableSubjects,
      GroupDto group, Set<LessonDto> lessons)
  {

    SubjectDto subject = pickRandomSubject(availableSubjects);
    SubjectLimitsDto subjectLimits = fetchSubjectLimitsFromGroupLimits(group.getGroupLimitsDto(), subject.getName());

    TimeSlotDto timeSlot = timeSlotsHolder.getTimeSlotByFields(WeekType.BOTH, day, group.getShift(), lessonNumber);
    TeacherDto teacher = teacherHolder.getTeacherById(subjectLimits.getTeacherDto().getId());

    RoomDto room = Objects.nonNull(subjectLimits.getRoomDto())
        ? roomsHolder.getRoomById(subjectLimits.getRoomDto().getId())
        : null;

    lessons.add(new LessonDto(null, group, teacher, subject, room, timeSlot));

    if (subjectLimits.getTeacherDto2() != null) {
      TeacherDto teacher2 = teacherHolder.getTeacherById(subjectLimits.getTeacherDto2().getId());
      RoomDto room2 = Objects.nonNull(subjectLimits.getRoomDto2())
          ? roomsHolder.getRoomById(subjectLimits.getRoomDto2().getId())
          : null;
      lessons.add(new LessonDto(null, group, teacher2, subject, room2, timeSlot));
    }
  }

  private void addHalfSubjectToLessons(
      WeekType week, DayOfWeek day, int lessonNumber, List<SubjectDto> halfSubjects,
      GroupDto group, Set<LessonDto> lessons)
  {

    SubjectDto subject = pickRandomSubject(halfSubjects);
    SubjectLimitsDto subjectLimits = fetchSubjectLimitsFromGroupLimits(group.getGroupLimitsDto(), subject.getName());

    if (subjectLimits.getTeacherDto2() != null) {
      throw new SubjectProcessingException(HALF_SUBJECT_CANT_HAVE_SUBGROUPS_ERROR);
    }

    TimeSlotDto timeSlot = timeSlotsHolder.getTimeSlotByFields(week, day, group.getShift(), lessonNumber);
    TeacherDto teacher = teacherHolder.getTeacherById(subjectLimits.getTeacherDto().getId());
    RoomDto room = Objects.nonNull(subjectLimits.getRoomDto())
        ? roomsHolder.getRoomById(subjectLimits.getRoomDto().getId())
        : null;

    lessons.add(new LessonDto(null, group, teacher, subject, room, timeSlot));
  }

  private SubjectLimitsDto fetchSubjectLimitsFromGroupLimits(GroupLimitsDto groupLimits, String subjectName) {
    return groupLimits.getSubjectLimitsDtoSet()
        .stream()
        .filter(limits -> limits.getSubjectDto().getName().equals(subjectName))
        .findFirst()
        .orElseThrow(() -> new SubjectProcessingException(CANT_FIND_SUBJECT_LIMITS_ERROR));
  }

  private SubjectDto pickRandomSubject(List<SubjectDto> subjects) {
    final int randomSubjectNum = random.nextInt(subjects.size());
    return subjects.remove(randomSubjectNum);
  }

  private Map<DayOfWeek, Integer> generateHoursPerDayMap(GroupLimitsDto groupLimits) {
    final Map<DayOfWeek, Integer> hoursByDayMap = new HashMap<>();
    final List<DayOfWeek> days = getListOfWorkingDays();

    int hoursPerWeek = groupLimits.getMaxHoursPerWeek();

    if (groupLimits.getInterschoolCombine() != null) {
      TimeSlotDto combine = groupLimits.getInterschoolCombine();
      setCombineDayHours(combine, days, hoursByDayMap);
      hoursPerWeek -= hoursByDayMap.get(combine.getDay());
    }

    fillHoursPerDayMap(hoursPerWeek, days, hoursByDayMap);
    return hoursByDayMap;
  }

  private void fillHoursPerDayMap(int hoursPerWeek, List<DayOfWeek> days, Map<DayOfWeek, Integer> hoursByDayMap) {
    final int meanNum = hoursPerWeek / days.size();
    final int remainder = hoursPerWeek % days.size();

    days.forEach(day -> hoursByDayMap.put(day, meanNum));

    IntStream.range(0, remainder).forEach(i -> {
      final int randomDayNum = random.nextInt(days.size());
      DayOfWeek randomDay = days.remove(randomDayNum);
      hoursByDayMap.put(randomDay, hoursByDayMap.get(randomDay) + 1);
    });
  }

  private void setCombineDayHours(TimeSlotDto combine, List<DayOfWeek> days,  Map<DayOfWeek, Integer> map) {
    int combineDayHours = combine.getLessonNumber() - 1;

    days.remove(combine.getDay());
    map.put(combine.getDay(), combineDayHours);
  }

  private List<SubjectDto> getAllSubjectsExceptHalfWithDuplicatesFromGroupLimits(GroupLimitsDto groupLimits) {
    return groupLimits.getSubjectLimitsDtoSet().stream()
        .flatMap(subjectLimits -> IntStream
            .range(0, (int) Math.floor(subjectLimits.getHours()))
            .mapToObj((i) -> subjectLimits.getSubjectDto()))
        .collect(Collectors.toList());
  }

  private List<SubjectDto> getHalfSubjectFromGroupLimits(GroupLimitsDto groupLimits) {
    return groupLimits.getSubjectLimitsDtoSet().stream()
        .filter(subjectLimits -> subjectLimits.getHours() != Math.floor(subjectLimits.getHours()))
        .map(SubjectLimitsDto::getSubjectDto)
        .collect(Collectors.toList());
  }

  private boolean isHalfSubjectPickTime(int lessonCountPerDay, int halfSubjectCount) {
    return random.nextInt(lessonCountPerDay * WORKING_DAYS_COUNT) < (halfSubjectCount / 2);
  }

  private List<DayOfWeek> getListOfWorkingDays() {
    return Stream.of(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ).collect(Collectors.toList());
  }

  private List<GroupDto> fetchGroupsFromDbByUserId(Long userId) {
    return groupRepository.findAllByUserId(userId)
        .stream()
        .map(groupMapper::groupToDto)
        .filter(group -> group.getGradeNumber() > 6 && group.getGradeNumber() < 11)
        .collect(Collectors.toList());
  }

}
