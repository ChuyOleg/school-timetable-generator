package ip91.chui.oleh.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

  private Long id;

  @NotBlank(message = "Name should be not empty")
  @Size(max = 64, message = "Max size of name is 64")
  private String name;

}
