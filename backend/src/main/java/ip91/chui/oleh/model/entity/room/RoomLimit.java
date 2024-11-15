package ip91.chui.oleh.model.entity.room;

import ip91.chui.oleh.model.entity.room.Room;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "room_limit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomLimit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectLimitsIdGenerator")
  @SequenceGenerator(name = "subjectLimitsIdGenerator", sequenceName = "room_limit_id_seq", allocationSize = 5)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Room room;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DayOfWeek day;

  @Column(nullable = false)
  private int shift;

  @Column(nullable = false)
  private int lessonNumberFrom;

  @Column(nullable = false)
  private int lessonNumberTo;
}
