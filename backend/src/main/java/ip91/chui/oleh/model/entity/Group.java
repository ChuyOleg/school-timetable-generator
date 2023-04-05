package ip91.chui.oleh.model.entity;

import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.Shift;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "class_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupIdGenerator")
  @SequenceGenerator(name = "groupIdGenerator", sequenceName = "class_group_id_seq", allocationSize = 10)
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GradeNumber gradeNumber;

  @Column(length = 1, nullable = false)
  private String letter;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Shift shift;

  @OneToMany(mappedBy = "group")
  private Set<Lesson> lessonSet;

  @OneToOne(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
  private GroupLimits groupLimits;

}
