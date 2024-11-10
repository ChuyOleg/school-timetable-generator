package ip91.chui.oleh.model.entity.teacher;

import ip91.chui.oleh.model.entity.teacher.limit.DesiredPeriodLimit;
import ip91.chui.oleh.model.entity.teacher.limit.FreeDayLimit;
import ip91.chui.oleh.model.entity.teacher.limit.LessonsOrderLimit;
import ip91.chui.oleh.model.entity.teacher.limit.MaxLessonsLimit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "teacher_limits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TeacherLimits {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherLimitsIdGenerator")
  @SequenceGenerator(name = "teacherLimitsIdGenerator", sequenceName = "teacher_limits_id_seq", allocationSize = 10)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  @EqualsAndHashCode.Include
  @ToString.Exclude
  private Teacher teacher;

  @OneToMany(mappedBy = "teacherLimits", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
  private Set<FreeDayLimit> freeDayLimits;

  @OneToOne(mappedBy = "teacherLimits", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private LessonsOrderLimit lessonsOrderLimit;

  @OneToOne(mappedBy = "teacherLimits", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private MaxLessonsLimit maxLessonsLimit;

  @OneToMany(mappedBy = "teacherLimits", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
  private Set<DesiredPeriodLimit> desiredPeriodLimits;
}
