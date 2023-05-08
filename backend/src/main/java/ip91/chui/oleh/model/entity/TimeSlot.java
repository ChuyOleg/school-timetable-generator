package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.enumeration.WeekType;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "time_slot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

  @Id
  private Long id;

  @Column(nullable = false)
  private int lessonNumber;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DayOfWeek day;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private WeekType weekType;

}
