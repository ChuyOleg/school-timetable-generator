package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.Shift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "class_group")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupIdGenerator")
  @SequenceGenerator(name = "groupIdGenerator", sequenceName = "class_group_id_seq", allocationSize = 10)
  private Long id;

  @Enumerated(EnumType.STRING)
  private GradeNumber gradeNumber;

  @Column
  private String letter;

  @Enumerated(EnumType.STRING)
  private Shift shift;

  @OneToMany(mappedBy = "group")
  private Set<Lesson> lessons;

//  @OneToOne
  @Transient
  private GroupLimits groupLimits;

}
