package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherIdGenerator")
  @SequenceGenerator(name = "teacherIdGenerator", sequenceName = "teacherIdSequence", allocationSize = 10)
  private Long id;

  @Column
  private String name;

  @ManyToMany()
  @JoinTable(
      name = "subject_teacher",
      joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
  )
  private Set<Subject> subjects;

  @OneToMany(mappedBy = "teacher")
  private Set<Lesson> lessons;

  @Column
  private int maxHoursPerWeek;

  public Teacher(String name, Set<Subject> subjects, int maxHoursPerWeek) {
    this.name = name;
    this.subjects = subjects;
    this.maxHoursPerWeek = maxHoursPerWeek;
  }
}
