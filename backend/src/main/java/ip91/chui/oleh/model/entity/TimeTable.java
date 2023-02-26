package ip91.chui.oleh.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class TimeTable {

  private Long timetableId;
  private List<Group> groups;

  public TimeTable(List<Group> groups) {
    this.groups = groups;
  }
}
