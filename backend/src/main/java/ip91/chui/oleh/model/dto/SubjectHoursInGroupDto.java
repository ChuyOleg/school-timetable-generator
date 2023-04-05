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
public class SubjectHoursInGroupDto {

  private Long id;

  @NotNull(message = "Subject should be specified")
  private SubjectDto subjectDto;

  @NotNull(message = "Hours should be specified")
  @Min(value = 1, message = "Min value of hours is 1")
  @Max(value = 10, message = "Max value of hours is 10")
  private double hours;

}
