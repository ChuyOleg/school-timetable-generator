package ip91.chui.oleh.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftsIntersectionDto {

  private Long id;

  @NotNull(message = "LessonNumber should be specified")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 8, message = "Max value of lessonNumber is 8")
  private int shiftOneLessonNumber;

  @NotNull(message = "LessonNumber should be specified")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 8, message = "Max value of lessonNumber is 8")
  private int shiftTwoLessonNumber;
}
