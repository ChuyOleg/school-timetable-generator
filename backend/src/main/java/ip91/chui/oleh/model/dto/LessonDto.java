package ip91.chui.oleh.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

  private Long id;

  @NotNull
  private TeacherDto teacherDto;

  @NotNull
  private SubjectDto subjectDto;

  @NotNull
  private RoomDto roomDto;

  @NotNull
  @Valid
  private TimeSlotDto timeSlotDto;

}
