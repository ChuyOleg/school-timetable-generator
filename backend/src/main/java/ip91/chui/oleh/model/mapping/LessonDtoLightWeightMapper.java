package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.lightweigth.LessonDtoLightWeight;
import ip91.chui.oleh.model.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
      @Mapping(target = "groupDto.id", source = "groupId"),
      @Mapping(target = "teacherDto.id", source = "teacherId"),
      @Mapping(target = "subjectDto.id", source = "subjectId"),
      @Mapping(target = "roomDto.id", source = "roomId"),
      @Mapping(target = "timeSlotDto.id", source = "timeSlotId")
  })
  LessonDto toLessonDto(LessonDtoLightWeight lessonDtoLightWeight);

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

}
