package ip91.chui.oleh.model.dto.teacher.limit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaxLessonsLimitDto {

  private Long id;

  @NotNull(message = "count should be specified")
  @Min(value = 1, message = "Min value of count is 1")
  @Max(value = 8, message = "Max value of count is 8")
  private int count;
}
