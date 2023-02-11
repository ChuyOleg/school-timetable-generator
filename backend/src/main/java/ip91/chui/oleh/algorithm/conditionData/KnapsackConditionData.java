package ip91.chui.oleh.algorithm.conditionData;

import java.util.Arrays;

public record KnapsackConditionData(int[] weightTable, int[] priceTable, int maxWeight) {

  @Override
  public String toString() {
    return "KnapsackConditionData: " + System.lineSeparator() +
        "    weightTable: " + Arrays.toString(weightTable) + System.lineSeparator() +
        "    priceTable:  " + Arrays.toString(priceTable) + System.lineSeparator() +
        "    maxWeight: " + maxWeight;
  }
}
