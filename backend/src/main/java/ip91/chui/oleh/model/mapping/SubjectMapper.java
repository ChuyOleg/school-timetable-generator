package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.model.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SubjectMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "name", source = "name")
  })
  SubjectDto subjectToDto(Subject subject);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "name", source = "name")
  })
  Subject dtoToSubject(SubjectDto subjectDto);

}
