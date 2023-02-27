package ip91.chui.oleh.model.dto;

import ip91.chui.oleh.model.entity.Subject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

  private Long id;

  @NotBlank(message = "Name should be not empty")
  @Size(max = 64, message = "Max size of name is 64")
  private String name;

  @NotEmpty(message = "Teacher should have been linked at least to 1 subject")
  @Valid
  private Set<SubjectDto> subjects;

  @NotNull(message = "Teacher should have maxHoursPerWeek value")
  @Min(value = 1, message = "Min value of maxHoursPerWeek is 1")
  @Max(value = 35, message = "Max value of maxHoursPerWeek is 35")
  private int maxHoursPerWeek;

}
