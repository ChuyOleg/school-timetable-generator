package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "group_limits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupLimits {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupLimitsIdGenerator")
  @SequenceGenerator(name = "groupLimitsIdGenerator", sequenceName = "group_limits_id_seq", allocationSize = 10)
  private Long id;

  @OneToOne
  @JoinColumn(name = "class_group_id", referencedColumnName = "id")
  private Group group;

  @OneToMany
  @JoinTable(
      name = "subject_teacher_in_group_mapping",
      joinColumns = {@JoinColumn(name = "group_limits_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")})
  @MapKeyJoinColumn(name = "subject_id")
  private Map<Subject, Teacher> subjectTeacherMap;

  @ElementCollection
  @CollectionTable(
      name = "subject_hours_in_group_mapping",
      joinColumns = {@JoinColumn(name = "group_limits_id", referencedColumnName = "id")}
  )
  @MapKeyJoinColumn(name = "subject_id")
  @Column(name = "hours", columnDefinition = "double precision")
  private Map<Subject, Double> subjectHoursMap;

  @Column
  private int maxHoursPerWeek;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "combine_time_slot_id", referencedColumnName = "id")
  private TimeSlot interschoolCombine;

}
