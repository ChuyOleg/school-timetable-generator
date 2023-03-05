package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonIdGenerator")
  @SequenceGenerator(name = "lessonIdGenerator", sequenceName = "lessonIdSequence", allocationSize = 10)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private Teacher teacher;

  @ManyToOne()
  @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
  private Subject subject;

  @ManyToOne()
  @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
  private Room room;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "time_slot_id", referencedColumnName = "id", nullable = false)
  private TimeSlot timeSlot;

  public Lesson(Subject subject, TimeSlot timeSlot) {
    this.subject = subject;
    this.timeSlot = timeSlot;
  }
}
