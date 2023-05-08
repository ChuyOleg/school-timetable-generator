package ip91.chui.oleh.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

  private Long id;

  @NotNull(message = "Group should have gradeNumber value")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 11, message = "Max value of lessonNumber is 11")
  private int gradeNumber;

  @NotBlank(message = "Group should have letter value")
  @Size(min = 1, max = 1, message = "Letter value should be one symbol")
  private String letter;

  @NotNull(message = "Group should have shift value")
  @Min(value = 1, message = "Min value of lessonNumber is 1")
  @Max(value = 2, message = "Max value of lessonNumber is 2")
  private int shift;

  @NotNull(message = "Group should have class teacher")
  private TeacherDto teacherDto;

  /* TODO: maybe it's better to create wrapper class with this field
      cause it's used only during algorithm calculations */
  @EqualsAndHashCode.Exclude
  private Set<LessonDto> lessons;

  @Valid
  @EqualsAndHashCode.Exclude
  private GroupLimitsDto groupLimitsDto;

}
