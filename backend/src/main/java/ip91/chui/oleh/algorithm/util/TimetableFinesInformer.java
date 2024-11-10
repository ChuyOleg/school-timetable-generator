package ip91.chui.oleh.algorithm.util;

import ip91.chui.oleh.model.dto.*;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TimetableFinesInformer {

  private static final int MAX_SAME_SUBJECT_AT_ONE_DAY_COUNT = 2;
  private static final int MAX_TEACHER_HAS_LESSONS_AT_SAME_TIME = 1;
  private static final int SHIFT_1 = 1;
  private static final int SHIFT_2 = 2;

  private final GroupsHolder groupsHolder;
  private final TimeSlotsHolder timeSlotsHolder;
  private final RoomsHolder roomsHolder;

  public TimeTableFinesDto check(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    TimeTableFinesDto timeTableFinesDto = new TimeTableFinesDto(new HashSet<>(), new HashSet<>(), new HashSet<>());

    Set<GroupDto> groups = getGroupsFromTimeTableDtoLightWeight(timeTableDtoLightWeight);

    List<LessonDto> shift1Lessons = getLessonsFromGroupsByShift(groups, (group -> group.getShift() == SHIFT_1));
    List<LessonDto> shift2Lessons = getLessonsFromGroupsByShift(groups, (group -> group.getShift() == SHIFT_2));

    checkTimeSlotCollision(shift1Lessons, timeTableFinesDto);
    checkTimeSlotCollision(shift2Lessons, timeTableFinesDto);

    groups.forEach(group -> checkSubjectsPerDay(group, timeTableFinesDto));

    groups.forEach(group -> {
      group.setLessons(null);
      group.setTeacherDto(null);
      group.setGroupLimitsDto(null);
    });

    return timeTableFinesDto;
  }

  private void checkTimeSlotCollision(List<LessonDto> lessons, TimeTableFinesDto timeTableFinesDto) {
    lessons.forEach(lesson -> {
      List<LessonDto> sameDayAndLessonNumberLessons = lessons
          .stream()
          .filter(l -> l.getTimeSlotDto().equals(lesson.getTimeSlotDto()))
          .toList();

      checkTeacherTimeSlotCollision(lesson, sameDayAndLessonNumberLessons, timeTableFinesDto);
      checkRoomTimeSlotCollision(lesson, sameDayAndLessonNumberLessons, timeTableFinesDto);
    });
  }

  /* teacher should have max 1 lesson at same time */
  private void checkTeacherTimeSlotCollision(LessonDto checkedLesson, List<LessonDto> lessons, TimeTableFinesDto timeTableFinesDto) {
    long sameTeacherCount = lessons
        .stream()
        .filter(lesson -> lesson.getTeacherDto().equals(checkedLesson.getTeacherDto()))
        .count();

    if (sameTeacherCount > MAX_TEACHER_HAS_LESSONS_AT_SAME_TIME) {
      timeTableFinesDto.getTeacherFines().add(checkedLesson);
    }
  }

  private void checkRoomTimeSlotCollision(LessonDto checkedLesson, List<LessonDto> lessons, TimeTableFinesDto timeTableFinesDto) {
    if (checkedLesson.getRoomDto() == null) {
      return;
    }

    long sameRoomCount = lessons
        .stream()
        .filter(lesson -> checkedLesson.getRoomDto().equals(lesson.getRoomDto()))
        .count();

    if (sameRoomCount > checkedLesson.getRoomDto().getCapacity()) {
      timeTableFinesDto.getRoomFines().add(checkedLesson);
    }
  }

  private void checkSubjectsPerDay(GroupDto groupDto, TimeTableFinesDto timeTableFinesDto) {
    getListOfWorkingDays().forEach(day -> {
      List<LessonDto> dayLessons = groupDto.getLessons()
          .stream()
          .filter(lesson -> lesson.getTimeSlotDto().getDay().equals(day))
          .toList();

      checkSubjectsPerSpecificDay(dayLessons, timeTableFinesDto);
    });
  }

  /* group should have max 2 same subjects per day */
  private void checkSubjectsPerSpecificDay(List<LessonDto> lessons, TimeTableFinesDto timeTableFinesDto) {
    lessons.forEach(lesson -> {
      long matchesCount = lessons
          .stream()
          .filter(l ->
              l.getSubjectDto().equals(lesson.getSubjectDto()) &&
              l.getTeacherDto().equals(lesson.getTeacherDto())
          )
          .count();

      if (matchesCount > MAX_SAME_SUBJECT_AT_ONE_DAY_COUNT) {
        timeTableFinesDto.getSubjectFines().add(lesson);
      }
    });
  }

  private Set<GroupDto> getGroupsFromTimeTableDtoLightWeight(TimeTableDtoLightWeight timeTableDtoLightWeight) {
    Map<Long, GroupDto> groupsById = new HashMap<>();
    Map<Long, TimeSlotDto> timeSlotsById = new HashMap<>();
    Map<Long, TeacherDto> teachersById = new HashMap<>();
    Map<Long, SubjectDto> subjectsById = new HashMap<>();
    Map<Long, RoomDto> roomsById = new HashMap<>();

    groupsHolder.getGroups().forEach(group -> {
      group.setLessons(new HashSet<>());
      groupsById.put(group.getId(), group);
    });
    timeSlotsHolder.getTimeSlots().forEach(timeSlot -> timeSlotsById.put(timeSlot.getId(), timeSlot));
    roomsHolder.getRooms().forEach(room -> roomsById.put(room.getId(), room));

    timeTableDtoLightWeight.getLessons().forEach(lesson -> {
      GroupDto group = groupsById.get(lesson.getGroupId());
      TeacherDto teacher = getTeacherFromTeachersById(lesson.getTeacherId(), teachersById);
      SubjectDto subject = getSubjectFromSubjectsById(lesson.getSubjectId(), subjectsById);
      RoomDto room = roomsById.get(lesson.getRoomId());
      TimeSlotDto timeSlot = timeSlotsById.get(lesson.getTimeSlotId());

      group.getLessons().add(
          new LessonDto(lesson.getId(), group, teacher, subject, room, timeSlot)
      );
    });

    return new HashSet<>(groupsById.values());
  }

  private TeacherDto getTeacherFromTeachersById(Long id, Map<Long, TeacherDto> teachersById) {
    teachersById.putIfAbsent(id, new TeacherDto(id, null, null, 0, null));
    return teachersById.get(id);
  }

  private SubjectDto getSubjectFromSubjectsById(Long id, Map<Long, SubjectDto> subjectsById) {
    subjectsById.putIfAbsent(id, new SubjectDto(id, null));
    return subjectsById.get(id);
  }

  private List<LessonDto> getLessonsFromGroupsByShift(Set<GroupDto> groups, Predicate<GroupDto > predicate) {
    return groups.stream()
        .filter(predicate)
        .flatMap(group -> group.getLessons().stream())
        .toList();
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

}
