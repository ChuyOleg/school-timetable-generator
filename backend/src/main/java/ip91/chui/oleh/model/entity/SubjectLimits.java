package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_limits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectLimits {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectLimitsIdGenerator")
  @SequenceGenerator(name = "subjectLimitsIdGenerator", sequenceName = "subject_limits_id_seq ", allocationSize = 10)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "group_limits_id", referencedColumnName = "id", nullable = false)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private GroupLimits groupLimits;

  @ManyToOne
  @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
  private Subject subject;

  @Column(nullable = false)
  private double hours;

  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  private Room room;

  @ManyToOne
  @JoinColumn(name = "teacher_2_id", referencedColumnName = "id")
  private Teacher teacher2;

  @ManyToOne
  @JoinColumn(name = "room_2_id", referencedColumnName = "id")
  private Room room2;

}
