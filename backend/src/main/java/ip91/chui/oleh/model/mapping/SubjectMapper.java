package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.entity.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

  SubjectDto subjectToDto(Subject subject);

  Subject dtoToSubject(SubjectDto subjectDto);

}
