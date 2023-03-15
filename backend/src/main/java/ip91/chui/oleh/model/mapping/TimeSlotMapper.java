package ip91.chui.oleh.model.mapping;

import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.entity.TimeSlot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {

  TimeSlotDto timeSlotToDto(TimeSlot timeSlot);

  TimeSlot dtoToTimeSlot(TimeSlotDto timeSlotDto);

}
