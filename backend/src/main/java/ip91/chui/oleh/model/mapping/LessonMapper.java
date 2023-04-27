package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {
    GroupMapper.class, TeacherMapper.class, SubjectMapper.class, RoomMapper.class, TimeSlotMapper.class
})
public interface LessonMapper {

  @Mappings({
      @Mapping(target = "groupDto", source = "group", qualifiedByName = {"groupToDto"}),
      @Mapping(target = "teacherDto", source = "teacher", qualifiedByName = {"teacherToDto"}),
      @Mapping(target = "subjectDto", source = "subject"),
      @Mapping(target = "roomDto", source = "room"),
      @Mapping(target = "timeSlotDto", source = "timeSlot")
  })
  LessonDto lessonToDto(Lesson lesson);

  @Mappings({
      @Mapping(target = "group", source = "groupDto"),
      @Mapping(target = "teacher", source = "teacherDto"),
      @Mapping(target = "subject", source = "subjectDto"),
      @Mapping(target = "room", source = "roomDto"),
      @Mapping(target = "timeSlot", source = "timeSlotDto")
  })
  Lesson dtoToLesson(LessonDto lessonDto);

}
