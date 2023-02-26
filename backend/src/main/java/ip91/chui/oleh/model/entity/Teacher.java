package ip91.chui.oleh.model.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class Teacher {

  private Long teacherId;
  private String name;
  private List<Subject> subject;
  private int maxHoursPerWeek;

  public Teacher(String name, List<Subject> subject, int maxHoursPerWeek) {
    this.name = name;
    this.subject = subject;
    this.maxHoursPerWeek = maxHoursPerWeek;
  }
}
