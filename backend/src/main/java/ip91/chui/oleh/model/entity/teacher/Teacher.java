package ip91.chui.oleh.model.entity.teacher;

import ip91.chui.oleh.model.entity.Subject;
import ip91.chui.oleh.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherIdGenerator")
  @SequenceGenerator(name = "teacherIdGenerator", sequenceName = "teacher_id_seq", allocationSize = 10)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  @EqualsAndHashCode.Include
  private String name;

  @Column(nullable = false)
  private int maxHoursPerWeek;

  @ManyToMany
  @JoinTable(
      name = "teacher_subject",
      joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
  )
  @EqualsAndHashCode.Exclude
  private Set<Subject> subjects;

  @OneToOne(mappedBy = "teacher", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @ToString.Exclude
  private TeacherLimits limits;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  @ToString.Exclude
  private User user;

  @Column(name = "created_date", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  @LastModifiedDate
  private LocalDateTime modifiedDate;
}
