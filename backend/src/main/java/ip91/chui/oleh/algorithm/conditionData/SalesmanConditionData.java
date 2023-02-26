package ip91.chui.oleh.algorithm.conditionData;

import java.util.Arrays;

public record SalesmanConditionData(int[][] roadMatrix) {

  @Override
  public String toString() {
    return "SalesmanConditionData: " + System.lineSeparator() +
        Arrays.stream(roadMatrix).reduce("",
            (acc, roadLine) -> acc + Arrays.toString(roadLine) + System.lineSeparator(), String::concat);
  }
}
