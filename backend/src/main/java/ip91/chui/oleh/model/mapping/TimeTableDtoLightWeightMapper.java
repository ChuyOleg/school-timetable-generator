package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TimeTableDto;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { LessonDtoLightWeightMapper.class })
public interface TimeTableDtoLightWeightMapper {

  TimeTableDtoLightWeight toTimeTableDtoLightWeight(TimeTableDto timeTableDto);

  TimeTableDto toTimeTableDto(TimeTableDtoLightWeight timeTableDtoLightWeight);

}
