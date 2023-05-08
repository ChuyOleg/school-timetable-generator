package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "class_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupIdGenerator")
  @SequenceGenerator(name = "groupIdGenerator", sequenceName = "class_group_id_seq", allocationSize = 10)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  @EqualsAndHashCode.Include
  private int gradeNumber;

  @Column(length = 1, nullable = false)
  @EqualsAndHashCode.Include
  private String letter;

  @Column(nullable = false)
  private int shift;

  @OneToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private Teacher teacher;

  @OneToOne(mappedBy = "group", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @ToString.Exclude
  private GroupLimits groupLimits;

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
