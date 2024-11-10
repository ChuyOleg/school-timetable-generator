package ip91.chui.oleh.model.dto.teacher;

import ip91.chui.oleh.model.dto.teacher.limit.DesiredPeriodLimitDto;
import ip91.chui.oleh.model.dto.teacher.limit.FreeDayLimitDto;
import ip91.chui.oleh.model.dto.teacher.limit.LessonsOrderLimitDto;
import ip91.chui.oleh.model.dto.teacher.limit.MaxLessonsLimitDto;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherLimitsDto {

  private Long id;

  private Set<@Valid FreeDayLimitDto> freeDayLimits;

  @Valid
  private LessonsOrderLimitDto lessonsOrderLimit;

  @Valid
  private MaxLessonsLimitDto maxLessonsLimit;

  private Set<@Valid DesiredPeriodLimitDto> desiredPeriodLimits;
}
