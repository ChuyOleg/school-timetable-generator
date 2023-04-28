package ip91.chui.oleh.model.dto.lightweigth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDtoLightWeight {

  private Long id;

  private Long groupId;

  private Long teacherId;

  private Long subjectId;

  private Long roomId;

  private Long timeSlotId;

}
