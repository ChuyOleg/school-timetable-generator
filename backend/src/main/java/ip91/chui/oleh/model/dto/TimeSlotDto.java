package ip91.chui.oleh.model.dto;

import ip91.chui.oleh.model.enumeration.WeekType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto {

  private Long id;

  @NotNull(message = "LessonNumber should be specified")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 8, message = "Max value of lessonNumber is 8")
  private int lessonNumber;

  @NotNull(message = "Day should be specified")
  private DayOfWeek day;

  @NotNull(message = "Group should have shift value")
  @Min(value = 1, message = "Min value of shift is 1")
  @Max(value = 2, message = "Max value of shift is 2")
  private int shift;

  @NotNull(message = "WeekType should be specified")
  private WeekType weekType;

}
