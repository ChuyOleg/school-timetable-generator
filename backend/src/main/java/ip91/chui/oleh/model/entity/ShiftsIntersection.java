package ip91.chui.oleh.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shifts_intersection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftsIntersection {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shiftsIntersectionIdGenerator")
  @SequenceGenerator(name = "shiftsIntersectionIdGenerator", sequenceName = "shifts_intersection_id_seq ", allocationSize = 3)
  private Long id;

  @Column(nullable = false)
  private int shiftOneLessonNumber;

  @Column(nullable = false)
  private int shiftTwoLessonNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;
}
