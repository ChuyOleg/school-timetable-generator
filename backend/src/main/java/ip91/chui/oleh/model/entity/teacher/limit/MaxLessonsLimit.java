package ip91.chui.oleh.model.entity.teacher.limit;

import ip91.chui.oleh.model.entity.teacher.TeacherLimits;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "max_lessons_limit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxLessonsLimit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maxLessonsLimitIdGenerator")
  @SequenceGenerator(name = "maxLessonsLimitIdGenerator", sequenceName = "max_lessons_limit_id_seq ", allocationSize = 10)
  private Long id;

  @Column(nullable = false)
  private int count;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_limits_id", referencedColumnName = "id", nullable = false)
  @ToString.Exclude
  private TeacherLimits teacherLimits;
}
