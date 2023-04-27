package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "group_limits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroupLimits {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupLimitsIdGenerator")
  @SequenceGenerator(name = "groupLimitsIdGenerator", sequenceName = "group_limits_id_seq", allocationSize = 10)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_group_id", referencedColumnName = "id", nullable = false)
  @EqualsAndHashCode.Include
  @ToString.Exclude
  private Group group;

  @OneToMany(mappedBy = "groupLimits", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SubjectLimits> subjectLimitsSet;

  @Column(nullable = false)
  private int maxHoursPerWeek;

  @ManyToOne
  @JoinColumn(name = "combine_time_slot_id", referencedColumnName = "id")
  private TimeSlot interschoolCombine;

}
