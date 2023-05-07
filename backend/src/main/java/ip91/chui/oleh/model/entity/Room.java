package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomIdGenerator")
  @SequenceGenerator(name = "roomIdGenerator", sequenceName = "room_id_seq", allocationSize = 5)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int capacity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;

}
