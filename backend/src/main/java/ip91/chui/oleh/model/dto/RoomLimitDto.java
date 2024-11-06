package ip91.chui.oleh.model.dto;

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
public class RoomLimitDto {

  private Long id;

  @NotNull(message = "Day should be specified")
  private DayOfWeek day;

  @NotNull(message = "LessonNumber should be specified")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 8, message = "Max value of lessonNumber is 8")
  private int lessonNumberFrom;

  @NotNull(message = "LessonNumber should be specified")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 8, message = "Max value of lessonNumber is 8")
  private int lessonNumberTo;

}
