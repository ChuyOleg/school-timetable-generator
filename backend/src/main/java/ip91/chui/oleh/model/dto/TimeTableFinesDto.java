package ip91.chui.oleh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableFinesDto {

  private Set<LessonDto> subjectFines;
  private Set<LessonDto> teacherFines;
  private Set<LessonDto> roomFines;

}
