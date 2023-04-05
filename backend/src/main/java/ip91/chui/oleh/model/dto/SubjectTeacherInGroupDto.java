package ip91.chui.oleh.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherInGroupDto {

  private Long id;

  @NotNull(message = "Subject should be specified")
  private SubjectDto subjectDto;

  @NotNull(message = "Teacher should be specified")
  private TeacherDto teacherDto;

}
