package ip91.chui.oleh.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

  private Long id;

  @NotNull(message = "Lesson should have teacher")
  private TeacherDto teacherDto;

  @NotNull(message = "Lesson should have subject")
  private SubjectDto subjectDto;

  @NotNull(message = "Lesson should have room")
  private RoomDto roomDto;

  @NotNull(message = "Lesson should have timeSlot")
  private TimeSlotDto timeSlotDto;

}
