package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.conditionData.KnapsackConditionData;
import ip91.chui.oleh.algorithm.model.Individual;
import lombok.RequiredArgsConstructor;

/**
 * FitnessFunction implementation for Knapsack Problem.
 * @author Chui Oleh
 */
@RequiredArgsConstructor
public class KnapsackFitnessFunction implements FitnessFunction {

  private final KnapsackConditionData conditionData;

  /**
   * Calculates the fitness of a given individual.
   * @param individual the individual whose fitness value will be calculated
   * @return the fitness value of the individual
   */
  @Override
  public int calculate(Individual individual) {
    int fitness = 0;
    int weight = 0;

    for (int gene = 0; gene < individual.getChromosome().length; gene++) {
      boolean isInKnapsack = (boolean) individual.getChromosome()[gene];

      if (isInKnapsack) {
        fitness += conditionData.priceTable()[gene];
        weight += conditionData.weightTable()[gene];
      }
    }

    if (isDead(weight)) {
      return Integer.MIN_VALUE;
    }

    return fitness;
  }

  /**
   * Determines whether the weight of the items in the knapsack is over the maximum allowed weight.
   * @param weight the current weight of the items in the knapsack
   * @return true if the weight is over the maximum allowed weight, false otherwise
   */
  private boolean isDead(int weight) {
    return weight > conditionData.maxWeight();
  }

}
