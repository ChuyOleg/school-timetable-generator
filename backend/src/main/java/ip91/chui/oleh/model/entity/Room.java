package ip91.chui.oleh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {

  private Long roomId;
  private int roomNumber;
  private int capacity;

}
