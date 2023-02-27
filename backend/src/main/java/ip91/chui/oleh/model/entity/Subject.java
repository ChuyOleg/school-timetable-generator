package ip91.chui.oleh.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "subject")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectIdGenerator")
  @SequenceGenerator(name = "subjectIdGenerator", sequenceName = "subjectIdSequence", allocationSize = 5)
  private Long id;

  @Column
  private String name;

  @ManyToMany(mappedBy = "subjects")
  private Set<Teacher> teachers;

  public Subject(String name) {
    this.name = name;
  }

}
