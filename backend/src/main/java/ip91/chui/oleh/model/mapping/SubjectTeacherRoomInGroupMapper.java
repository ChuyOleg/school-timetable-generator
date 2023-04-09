package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.SubjectTeacherRoomInGroupDto;
import ip91.chui.oleh.model.entity.SubjectTeacherRoomInGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { SubjectMapper.class, TeacherMapper.class, RoomMapper.class })
public interface SubjectTeacherRoomInGroupMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "subjectDto", source = "subject"),
      @Mapping(target = "teacherDto", source = "teacher", qualifiedByName = {"teacherToDto"}),
      @Mapping(target = "roomDto", source = "room")
  })
  SubjectTeacherRoomInGroupDto SubjectTeacherInGroupToDto(SubjectTeacherRoomInGroup subjectTeacherRoomInGroup);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "subject", source = "subjectDto"),
      @Mapping(target = "teacher", source = "teacherDto"),
      @Mapping(target = "room", source = "roomDto")
  })
  SubjectTeacherRoomInGroup dtoToSubjectTeacherInGroup(SubjectTeacherRoomInGroupDto subjectTeacherRoomInGroupDto);

}
