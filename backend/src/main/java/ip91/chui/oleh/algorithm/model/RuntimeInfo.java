package ip91.chui.oleh.algorithm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RuntimeInfo {

  @Setter
  private Individual bestIndividual;
  private int bestIndividualNotChangeCounter;

  public void incrementBestIndividualNotChangeCounter() {
    bestIndividualNotChangeCounter += 1;
  }

  public void resetBestIndividualNotChangeCounter() {
    bestIndividualNotChangeCounter = 0;
  }
}
