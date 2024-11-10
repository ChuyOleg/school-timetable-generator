package ip91.chui.oleh.model.dto.teacher;

import ip91.chui.oleh.model.dto.SubjectDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TeacherDto {

  @EqualsAndHashCode.Include
  private Long id;

  @NotBlank(message = "Name should be not empty")
  @Size(max = 64, message = "Max size of name is 64")
  private String name;

  @NotEmpty(message = "Teacher should have been linked at least to 1 subject")
  private Set<SubjectDto> subjectDtoSet;

  @NotNull(message = "Teacher should have maxHoursPerWeek value")
  @Min(value = 1, message = "Min value of maxHoursPerWeek is 1")
  @Max(value = 40, message = "Max value of maxHoursPerWeek is 35")
  private int maxHoursPerWeek;

  @Valid
  private TeacherLimitsDto limits;
}
