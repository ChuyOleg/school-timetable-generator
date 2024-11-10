package ip91.chui.oleh.model.dto;


import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectLimitsDto {

  private Long id;

  @NotNull(message = "Subject should be specified")
  private SubjectDto subjectDto;

  @NotNull(message = "Hours should be specified")
  @Min(value = 0, message = "Min value of hours is 0")
  @Max(value = 10, message = "Max value of hours is 10")
  private double hours;

  @NotNull(message = "Teacher should be specified")
  private TeacherDto teacherDto;

  private RoomDto roomDto;

  private TeacherDto teacherDto2;

  private RoomDto roomDto2;

}
