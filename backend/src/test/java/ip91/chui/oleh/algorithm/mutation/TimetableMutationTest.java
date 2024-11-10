package ip91.chui.oleh.algorithm.mutation;

import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.util.TimeSlotsHolder;
import ip91.chui.oleh.model.dto.*;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableMutationTest {

  private static long nextLessonId = 1L;

  @Mock
  private FitnessFunction fitnessFunction;
  @Mock
  private Random random;
  @Mock
  private TimeSlotsHolder timeSlotsHolder;
  @InjectMocks
  private TimetableMutation mutation;

  @Test
  void ShouldNot_Mutate() {
    List<Individual> individuals = new ArrayList<>();

    GroupDto groupDto = new GroupDto();
    Set<LessonDto> lessonDtoSet = createLessons(groupDto);
    groupDto.setLessons(lessonDtoSet);
    Object[] chromosome = { groupDto };
    Individual individual = new Individual(chromosome);

    individuals.add(individual);

    when(random.nextInt(Config.MUTATION_MEASURE)).thenReturn(Config.MUTATION_PERCENTAGE);

    mutation.process(individuals);

    verify(random, Mockito.times(1)).nextInt(anyInt());
    verify(fitnessFunction).calculate(individual);
  }

  @Test
  void Should_Mutate() {
    List<Individual> individuals = new ArrayList<>();

    GroupDto groupDto = new GroupDto();
    Set<LessonDto> lessonDtoSet = createLessons(groupDto);
    groupDto.setLessons(lessonDtoSet);
    Object[] chromosome = { groupDto };
    Individual individual = new Individual(chromosome);

    individuals.add(individual);

    when(random.nextInt(Config.MUTATION_MEASURE)).thenReturn(Config.MUTATION_PERCENTAGE - 1);
    when(random.nextInt(lessonDtoSet.size())).thenCallRealMethod();

    mutation.process(individuals);

    verify(random, Mockito.times(3)).nextInt(anyInt());
    verify(fitnessFunction).calculate(individual);
  }

  private Set<LessonDto> createLessons(GroupDto group) {
    Set<LessonDto> lessons = new HashSet<>();

    for (int lessonNum = 1; lessonNum < 8; lessonNum++) {
      TeacherDto teacherDto = getDefaultTeacherDto();
      SubjectDto subjectDto = getSubjectByLessonNumber(lessonNum);
      RoomDto roomDto = getDefaultRoomDto();
      TimeSlotDto timeSlotDto = getTimeSlotByLessonNumber(lessonNum);

      LessonDto lesson = new LessonDto(nextLessonId++, group, teacherDto, subjectDto, roomDto, timeSlotDto);
      lessons.add(lesson);
    }

    return lessons;
  }

  private TeacherDto getDefaultTeacherDto() {
    return new TeacherDto(1L, "Default", null, 18, null);
  }

  private RoomDto getDefaultRoomDto() {
    return new RoomDto(1L, "Default", 2, null);
  }

  private SubjectDto getSubjectByLessonNumber(int lessonNumber) {
    return switch (lessonNumber) {
      case 1 -> new SubjectDto(1L, "Subj 1");
      case 2 -> new SubjectDto(2L, "Subj 2");
      case 3 -> new SubjectDto(3L, "Subj 3");
      case 4 -> new SubjectDto(4L, "Subj 4");
      case 5 -> new SubjectDto(5L, "Subj 5");
      case 6 -> new SubjectDto(6L, "Subj 6");
      case 7 -> new SubjectDto(7L, "Subj 7");
      default -> throw new RuntimeException();
    };
  }

  private TimeSlotDto getTimeSlotByLessonNumber(int lessonNumber) {
    return switch (lessonNumber) {
      case 1 -> new TimeSlotDto(1L, 1, DayOfWeek.MONDAY, WeekType.BOTH);
      case 2 -> new TimeSlotDto(2L, 2, DayOfWeek.MONDAY, WeekType.BOTH);
      case 3 -> new TimeSlotDto(3L, 3, DayOfWeek.MONDAY, WeekType.BOTH);
      case 4 -> new TimeSlotDto(4L, 4, DayOfWeek.MONDAY, WeekType.BOTH);
      case 5 -> new TimeSlotDto(5L, 5, DayOfWeek.MONDAY, WeekType.BOTH);
      case 6 -> new TimeSlotDto(6L, 6, DayOfWeek.MONDAY, WeekType.BOTH);
      case 7 -> new TimeSlotDto(7L, 7, DayOfWeek.MONDAY, WeekType.BOTH);
      default -> throw new RuntimeException();
    };
  }

}