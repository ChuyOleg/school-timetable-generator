package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomIdGenerator")
  @SequenceGenerator(name = "roomIdGenerator", sequenceName = "roomIdSequence", allocationSize = 10)
  private Long id;

  @Column
  private String name;

  @Column
  private int capacity;

}
