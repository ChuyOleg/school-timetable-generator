package ip91.chui.oleh.algorithm.mutation;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.util.holder.TimeSlotsHolder;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.enumeration.WeekType;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class NaturalMutation implements Mutation {

  private final TimeSlotsHolder timeSlotsHolder;
  private final Random random;

  @Override
  public void process(List<Individual> individuals, int mutationMeasure, int mutationPercentage) {
    individuals.forEach(individual -> {
      for (int geneNum = 0; geneNum < individual.getChromosome().length; geneNum++) {
        if (shouldMutate(mutationMeasure, mutationPercentage)) {
          GroupDto group = (GroupDto) individual.getChromosome()[geneNum];
          Object[] lessons = group.getLessons().toArray();

          int randomLessonNum1 = random.nextInt(lessons.length);
          int randomLessonNum2 = random.nextInt(lessons.length);

          TimeSlotDto timeSlot1 = ((LessonDto) lessons[randomLessonNum1]).getTimeSlotDto();
          TimeSlotDto timeSlot2 = ((LessonDto) lessons[randomLessonNum2]).getTimeSlotDto();

          swapTimeSlots(group, timeSlot1, timeSlot2);
        }
      }
    });
  }

  private void swapTimeSlots(GroupDto group, TimeSlotDto timeSlot1, TimeSlotDto timeSlot2) {
    if (timeSlot1.equals(timeSlot2)) {
      return;
    }

    if (timeSlot1.getWeekType().equals(WeekType.BOTH) && timeSlot2.getWeekType().equals(WeekType.BOTH)) {
      swapBothWeekTimeSlots(group, timeSlot1, timeSlot2);
    } else {
      swapDiffWeekTimeSlots(group, timeSlot1, timeSlot2);
    }
  }

  /** stream is used to consider subgroups (groups can have 2 same lessons (subgroup 1 and subgroup 2)) */
  private void swapBothWeekTimeSlots(GroupDto group, TimeSlotDto timeSlot1, TimeSlotDto timeSlot2) {
    List<LessonDto> lessons1 = group.getLessons()
        .stream()
        .filter(lesson -> lesson.getTimeSlotDto().equals(timeSlot1))
        .toList();

    List<LessonDto> lessons2 = group.getLessons()
        .stream()
        .filter(lesson -> lesson.getTimeSlotDto().equals(timeSlot2))
        .toList();

    lessons1.forEach(lesson -> lesson.setTimeSlotDto(timeSlot2));
    lessons2.forEach(lesson -> lesson.setTimeSlotDto(timeSlot1));
  }

  /** stream is used to consider subgroups (groups can have 2 same lessons (subgroup 1 and subgroup 2))
   *  TimeSlotsHolder is used because timeslots with diff week type have diff ID*/
  private void swapDiffWeekTimeSlots(GroupDto group, TimeSlotDto timeSlot1, TimeSlotDto timeSlot2) {
    List<LessonDto> lessons1 = group.getLessons()
        .stream()
        .filter(lesson -> isLessonsMatchDayAndLessonNumber(lesson, timeSlot1.getDay(), timeSlot1.getLessonNumber()))
        .toList();

    List<LessonDto> lessons2 = group.getLessons()
        .stream()
        .filter(lesson -> isLessonsMatchDayAndLessonNumber(lesson, timeSlot2.getDay(), timeSlot2.getLessonNumber()))
        .toList();

    lessons1.forEach(lesson -> lesson.setTimeSlotDto(timeSlotsHolder.getTimeSlotByFields(
        lesson.getTimeSlotDto().getWeekType(),
        timeSlot2.getDay(), group.getShift(), timeSlot2.getLessonNumber()) ));

    lessons2.forEach(lesson -> lesson.setTimeSlotDto(timeSlotsHolder.getTimeSlotByFields(
        lesson.getTimeSlotDto().getWeekType(),
        timeSlot1.getDay(), group.getShift(), timeSlot1.getLessonNumber()) ));
  }

  private boolean isLessonsMatchDayAndLessonNumber(LessonDto lesson, DayOfWeek day, int lessonNumber) {
    return lesson.getTimeSlotDto().getDay().equals(day) && lesson.getTimeSlotDto().getLessonNumber() == lessonNumber;
  }

  private boolean shouldMutate(int mutationMeasure, int mutationPercentage) {
    return random.nextInt(mutationMeasure) < mutationPercentage;
  }
}
