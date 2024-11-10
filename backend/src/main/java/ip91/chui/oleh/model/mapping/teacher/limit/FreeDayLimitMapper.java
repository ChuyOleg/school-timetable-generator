package ip91.chui.oleh.model.mapping.teacher.limit;

import ip91.chui.oleh.model.dto.teacher.limit.FreeDayLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.FreeDayLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreeDayLimitMapper {

  FreeDayLimitDto toDto(FreeDayLimit limit);

  FreeDayLimit toEntity(FreeDayLimitDto limitDto);
}
