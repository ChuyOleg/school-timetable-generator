package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "time_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TimeTable {

  @Id
  @Column
  private Long id;

  @OneToMany(mappedBy = "timeTable")
  private Set<Lesson> lessons;

}
