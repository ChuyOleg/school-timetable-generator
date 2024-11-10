package ip91.chui.oleh.model.dto.teacher.limit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesiredPeriodLimitDto {

  private Long id;

  @NotNull(message = "Day should be specified")
  private DayOfWeek day;

  @NotNull(message = "LessonFrom should be specified")
  @Min(value = 1, message = "Min value of lessonFrom is 1")
  @Max(value = 8, message = "Max value of lessonFrom is 8")
  private int lessonFrom;

  @NotNull(message = "LessonTo should be specified")
  @Min(value = 1, message = "Min value of lessonTo is 1")
  @Max(value = 8, message = "Max value of lessonTo is 8")
  private int lessonTo;
}
