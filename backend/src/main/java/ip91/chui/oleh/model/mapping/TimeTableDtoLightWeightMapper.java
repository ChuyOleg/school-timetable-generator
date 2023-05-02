package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TimeTableDto;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.entity.TimeTable;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { LessonDtoLightWeightMapper.class })
public interface TimeTableDtoLightWeightMapper {

  TimeTableDtoLightWeight toTimeTableDtoLightWeight(TimeTableDto timeTableDto);

  TimeTableDtoLightWeight toTimeTableDtoLightWeight(TimeTable timeTable);

  TimeTable toTimeTable(TimeTableDtoLightWeight timeTableDtoLightWeight);

  @AfterMapping
  default void setTimeTableForLessons(@MappingTarget TimeTable timeTable) {
    if (timeTable.getLessons() != null) {
      timeTable.getLessons().forEach(l -> l.setTimeTable(timeTable));
    }
  }

}
