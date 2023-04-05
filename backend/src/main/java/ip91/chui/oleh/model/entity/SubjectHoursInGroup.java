package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_hours_in_group_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectHoursInGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectHoursInGroupIdGenerator")
  @SequenceGenerator(name = "subjectHoursInGroupIdGenerator", sequenceName = "subject_hours_in_group_mapping_id_seq", allocationSize = 10)
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

}
