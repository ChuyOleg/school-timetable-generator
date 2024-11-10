package ip91.chui.oleh.model.entity.room;

import ip91.chui.oleh.model.entity.User;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomIdGenerator")
  @SequenceGenerator(name = "roomIdGenerator", sequenceName = "room_id_seq", allocationSize = 5)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int capacity;

  @OneToMany(mappedBy = "room", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
  private Set<RoomLimit> roomLimitSet;

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
