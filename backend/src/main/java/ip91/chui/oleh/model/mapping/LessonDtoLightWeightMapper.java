package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.lightweigth.LessonDtoLightWeight;
import ip91.chui.oleh.model.entity.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonDtoLightWeightMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "groupId", source = "groupDto.id"),
      @Mapping(target = "teacherId", source = "teacherDto.id"),
      @Mapping(target = "subjectId", source = "subjectDto.id"),
      @Mapping(target = "roomId", source = "roomDto.id"),
      @Mapping(target = "timeSlotId", source = "timeSlotDto.id")
  })
  LessonDtoLightWeight toLessonDtoLightWeight(LessonDto lessonDto);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "groupId", source = "group.id"),
      @Mapping(target = "teacherId", source = "teacher.id"),
      @Mapping(target = "subjectId", source = "subject.id"),
      @Mapping(target = "roomId", source = "room.id"),
      @Mapping(target = "timeSlotId", source = "timeSlot.id")
  })
  LessonDtoLightWeight toLessonDtoLightWeight(Lesson lesson);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "group.id", source = "groupId"),
      @Mapping(target = "teacher.id", source = "teacherId"),
      @Mapping(target = "subject.id", source = "subjectId"),
      @Mapping(target = "room.id", source = "roomId"),
      @Mapping(target = "timeSlot.id", source = "timeSlotId")
  })
  Lesson toLesson(LessonDtoLightWeight lessonDtoLightWeight);

  @AfterMapping
  default void setRoomNullIfRoomIdIsNull(@MappingTarget Lesson lesson) {
    if (lesson.getId() == null) {
      lesson.setRoom(null);
    }
  }

}
