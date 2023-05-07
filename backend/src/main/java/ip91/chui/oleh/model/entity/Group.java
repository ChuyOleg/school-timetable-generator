package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "class_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupIdGenerator")
  @SequenceGenerator(name = "groupIdGenerator", sequenceName = "class_group_id_seq", allocationSize = 10)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  @EqualsAndHashCode.Include
  private int gradeNumber;

  @Column(length = 1, nullable = false)
  @EqualsAndHashCode.Include
  private String letter;

  @Column(nullable = false)
  private int shift;

  @OneToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private Teacher teacher;

  @OneToOne(mappedBy = "group", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @ToString.Exclude
  private GroupLimits groupLimits;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  @ToString.Exclude
  private User user;

}
