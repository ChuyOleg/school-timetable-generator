package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "time_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {

  @Id
  @Column
  private Long id;

  @OneToMany(mappedBy = "timeTable")
  private Set<Lesson> lessons;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;

}
