package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.ShiftsIntersectionDto;
import ip91.chui.oleh.model.entity.ShiftsIntersection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShiftsIntersectionMapper {

  ShiftsIntersectionDto toDto(ShiftsIntersection shiftsIntersection);

  ShiftsIntersection toEntity(ShiftsIntersectionDto shiftsIntersectionDto);
}
