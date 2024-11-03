package ip91.chui.oleh.algorithm.fitnessFunction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class FitnessFunctionContext {

  private int fitnessScore;

  public void increaseFitnessScore(int value) {
    fitnessScore += value;
  }
}
