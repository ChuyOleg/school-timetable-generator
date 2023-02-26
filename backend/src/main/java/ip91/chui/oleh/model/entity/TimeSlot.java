package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.enumeration.LessonNumber;
import ip91.chui.oleh.model.enumeration.WeekType;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class TimeSlot {

  private Long timeSlotId;
  private LessonNumber lessonNumber;
//  private LocalTime startTime;
//  private LocalTime endTime;
   private DayOfWeek day;
  private WeekType weekType;

  public TimeSlot(LessonNumber lessonNumber, DayOfWeek day, WeekType weekType) {
    this.lessonNumber = lessonNumber;
    this.day = day;
    this.weekType = weekType;
  }
}
