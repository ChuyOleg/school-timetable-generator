package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.model.dto.LessonDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public final class FitnessFunctionContext {

  private final List<LessonDto> lessons;
  private int fitnessScore;
  private final Integer bestFitnessScoreFromPreviousGeneration;

  public void increaseFitnessScore(int value) {
    fitnessScore += value;
  }
}
