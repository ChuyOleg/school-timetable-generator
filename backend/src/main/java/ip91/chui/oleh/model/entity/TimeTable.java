package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "time_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TimeTable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timetableIdGenerator")
  @SequenceGenerator(name = "timetableIdGenerator", sequenceName = "time_table_id_seq", allocationSize = 5)
  private Long id;

  @OneToMany(mappedBy = "timeTable", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<Lesson> lessons;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;

  @Column(name = "created_date", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  @LastModifiedDate
  private LocalDateTime modifiedDate;

}
