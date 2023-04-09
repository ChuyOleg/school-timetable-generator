package ip91.chui.oleh.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupLimitsDto {

  private Long id;

  @NotEmpty(message = "Group should have info about mapping subjects and teachers")
  private Set<@Valid SubjectTeacherRoomInGroupDto> subjectTeacherRoomInGroupDtoSet;

  @NotEmpty(message = "Group should have info about hours per every subject")
  private Set<@Valid SubjectHoursInGroupDto> subjectHoursInGroupDtoSet;

  @NotNull(message = "GroupLimits should have have maxHoursPerWeek value")
  @Min(value = 1, message = "Min value of maxHoursPerWeek is 1")
  @Max(value = 40, message = "Max value of maxHoursPerWeek is 40")
  private int maxHoursPerWeek;

  @Valid
  private TimeSlotDto interschoolCombine;

}
