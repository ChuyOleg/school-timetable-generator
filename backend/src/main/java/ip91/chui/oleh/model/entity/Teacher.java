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

  @ManyToMany()
  @JoinTable(
      name = "teacher_subject",
      joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
  )
  private Set<Subject> subjects;

  @OneToMany(mappedBy = "teacher")
  private Set<Lesson> lessons;

  @Column(nullable = false)
  private int maxHoursPerWeek;

}
