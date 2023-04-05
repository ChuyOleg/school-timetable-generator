package ip91.chui.oleh.model.dto;

import ip91.chui.oleh.model.enumeration.GradeNumber;
import ip91.chui.oleh.model.enumeration.Shift;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

  private Long id;

  @NotNull(message = "Group should have gradeNumber value")
  private GradeNumber gradeNumber;

  @NotBlank(message = "Group should have letter value")
  @Size(min = 1, max = 1, message = "Letter value should be one symbol")
  private String letter;

  @NotNull(message = "Group should have shift value")
  private Shift shift;

  private Set<@Valid LessonDto> lessonDtoSet;

  @Valid
  private GroupLimitsDto groupLimitsDto;

}
