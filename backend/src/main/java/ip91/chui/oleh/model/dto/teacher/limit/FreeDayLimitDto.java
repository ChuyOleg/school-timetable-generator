package ip91.chui.oleh.model.dto.teacher.limit;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeDayLimitDto {

  private Long id;

  @NotNull(message = "Day should be specified.")
  private DayOfWeek day;
}
