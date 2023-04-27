package ip91.chui.oleh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableDto {

  private Long id;
  private Set<LessonDto> lessons;

}
