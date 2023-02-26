package ip91.chui.oleh.model.entity;

import lombok.Data;

@Data
public class Lesson {

  private Long  lessonId;
  private Teacher teacher;
  // Class
  private Subject subject;
  private Room room;
  // equals and HashCode for weekNumber, day, schoolShift and timeSlot;
  private TimeSlot timeSlot;

  public Lesson(Subject subject, TimeSlot timeSlot) {
    this.subject = subject;
    this.timeSlot = timeSlot;
  }
}
