package ip91.chui.oleh.model.entity.teacher.limit;

import ip91.chui.oleh.model.entity.teacher.TeacherLimits;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "free_day_limit")
@Data
@NoArgsConstructor
public class FreeDayLimit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freeDayLimitIdGenerator")
  @SequenceGenerator(name = "freeDayLimitIdGenerator", sequenceName = "free_day_limit_id_seq ", allocationSize = 10)
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DayOfWeek day;

  @ManyToOne
  @JoinColumn(name = "teacher_limits_id", referencedColumnName = "id", nullable = false)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private TeacherLimits teacherLimits;

  public FreeDayLimit(Long id, DayOfWeek day) {
    this.id = id;
    this.day = day;
  }
}
