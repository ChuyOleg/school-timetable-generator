package ip91.chui.oleh.model.dto.lightweigth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableDtoLightWeight {

  private Long id;
  private Set<LessonDtoLightWeight> lessons;

}
