package ip91.chui.oleh.service;

import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.model.mapping.TimeSlotMapper;
import ip91.chui.oleh.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

  private final TimeSlotRepository timeSlotRepository;
  private final TimeSlotMapper timeSlotMapper;

  public List<TimeSlotDto> getAll() {
    return timeSlotRepository.findAll()
        .stream()
        .map(timeSlotMapper::timeSlotToDto)
        .collect(Collectors.toList());
  }

}
