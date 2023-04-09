package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_teacher_room_in_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectTeacherRoomInGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectTeacherInGroupIdGenerator")
  @SequenceGenerator(name = "subjectTeacherInGroupIdGenerator", sequenceName = "subject_teacher_room_in_group_id_seq ", allocationSize = 10)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "group_limits_id", referencedColumnName = "id", nullable = false)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private GroupLimits groupLimits;

  @ManyToOne
  @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  private Room room;

}
