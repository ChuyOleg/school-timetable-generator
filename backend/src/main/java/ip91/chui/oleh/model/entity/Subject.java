package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectIdGenerator")
  @SequenceGenerator(name = "subjectIdGenerator", sequenceName = "subject_id_seq", allocationSize = 10)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;

  @Column(name = "created_date", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  @LastModifiedDate
  private LocalDateTime modifiedDate;

}
