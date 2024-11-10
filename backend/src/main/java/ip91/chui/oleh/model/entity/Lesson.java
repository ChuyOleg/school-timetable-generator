package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.entity.room.Room;
import ip91.chui.oleh.model.entity.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonIdGenerator")
  @SequenceGenerator(name = "lessonIdGenerator", sequenceName = "lesson_id_seq", allocationSize = 500)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "time_slot_id", referencedColumnName = "id", nullable = false)
  private TimeSlot timeSlot;

  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  private Room room;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "time_table_id", referencedColumnName = "id")
  private TimeTable timeTable;

}
