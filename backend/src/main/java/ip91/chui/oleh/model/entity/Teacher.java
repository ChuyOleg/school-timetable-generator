package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherIdGenerator")
  @SequenceGenerator(name = "teacherIdGenerator", sequenceName = "teacher_id_seq", allocationSize = 10)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "teacher_subject",
      joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
  )
  @EqualsAndHashCode.Exclude
  private Set<Subject> subjects;

  @Column(nullable = false)
  private int maxHoursPerWeek;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;

}
