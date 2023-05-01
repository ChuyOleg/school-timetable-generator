package ip91.chui.oleh.service;

import ip91.chui.oleh.algorithm.EvolutionaryAlgorithm;
import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.model.mapping.TimeTableDtoLightWeightMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeTableService {

  private final TimeTableDtoLightWeightMapper timeTableDtoLightWeightMapper;

  private final EvolutionaryAlgorithm algorithm;

  public TimeTableDtoLightWeight generate() {
    return timeTableDtoLightWeightMapper.toTimeTableDtoLightWeight(algorithm.generate());
  }

}
