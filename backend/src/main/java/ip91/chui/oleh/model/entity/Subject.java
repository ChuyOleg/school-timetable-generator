package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectIdGenerator")
  @SequenceGenerator(name = "subjectIdGenerator", sequenceName = "subjectIdSequence", allocationSize = 5)
  private Long id;


  private String name;

  public Subject(String name) {
    this.name = name;
  }

}