package ip91.chui.oleh.model.dto.teacher.limit;

import ip91.chui.oleh.model.enumeration.ImportanceLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonsOrderLimitDto {

  private Long id;

  @NotNull(message = "Importance level should be specified.")
  private ImportanceLevel importanceLevel;
}
