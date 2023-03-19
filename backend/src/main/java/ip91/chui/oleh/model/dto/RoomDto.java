package ip91.chui.oleh.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

  private Long id;

  @NotBlank(message = "Name should be not empty")
  @Size(max = 64, message = "Max size of name is 64")
  private String roomName;

  @NotNull(message = "Capacity should be not null")
  @Min(value = 1, message = "Min value of capacity is 1")
  private int capacity;

}
