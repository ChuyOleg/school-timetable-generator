package ip91.chui.oleh.model.dto;

import ip91.chui.oleh.model.enumeration.LessonNumber;
import ip91.chui.oleh.model.enumeration.WeekType;
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
  private LessonNumber lessonNumber;

  @NotNull(message = "Day should be specified")
  private DayOfWeek day;

  @NotNull(message = "WeekType should be specified")
  private WeekType weekType;

}
