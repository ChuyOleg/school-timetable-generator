package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectHoursInGroupDto;
import ip91.chui.oleh.model.entity.SubjectHoursInGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class })
public interface SubjectHoursInGroupMapper {

  @Mapping(target = "subjectDto", source = "subject")
  SubjectHoursInGroupDto SubjectHoursInGroupToDto(SubjectHoursInGroup subjectHoursInGroup);

  @Mapping(target = "subject", source = "subjectDto")
  SubjectHoursInGroup dtoToSubjectHoursInGroup(SubjectHoursInGroupDto subjectHoursInGroupDto);

}
