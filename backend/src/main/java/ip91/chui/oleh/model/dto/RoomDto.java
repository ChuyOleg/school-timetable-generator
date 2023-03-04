package ip91.chui.oleh.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

  private Long id;

  @NotNull(message = "RoomNumber should be not null")
  @Min(value = 1, message = "Min value of roomNumber is 1")
  private int roomNumber;

  @NotNull(message = "Capacity should be not null")
  @Min(value = 1, message = "Min value of capacity is 1")
  private int capacity;

}
