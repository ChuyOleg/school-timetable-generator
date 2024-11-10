package ip91.chui.oleh.model.mapping.teacher.limit;

import ip91.chui.oleh.model.dto.teacher.limit.DesiredPeriodLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.DesiredPeriodLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DesiredPeriodLimitMapper {

  DesiredPeriodLimitDto toDto(DesiredPeriodLimit limit);

  DesiredPeriodLimit toEntity(DesiredPeriodLimitDto limitDto);
}
