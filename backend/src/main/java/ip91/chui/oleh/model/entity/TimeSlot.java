package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.enumeration.LessonNumber;
import ip91.chui.oleh.model.enumeration.WeekType;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "time_slot")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeSlot {

  @Id
  @Column
  private Long id;

  @Enumerated(EnumType.STRING)
  private LessonNumber lessonNumber;

  @Enumerated(EnumType.STRING)
  private DayOfWeek day;

  @Enumerated(EnumType.STRING)
  private WeekType weekType;

  public TimeSlot(LessonNumber lessonNumber, DayOfWeek day, WeekType weekType) {
    this.lessonNumber = lessonNumber;
    this.day = day;
    this.weekType = weekType;
  }
}
