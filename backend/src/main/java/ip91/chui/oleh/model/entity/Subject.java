package ip91.chui.oleh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subject {

  private Long subjectId;
  private String name;

  public Subject(String name) {
    this.name = name;
  }

}
