package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.algorithm.model.Individual;
import lombok.RequiredArgsConstructor;

/**
 * FitnessFunction implementation for Salesman Problem.
 * @author Chui Oleh
 */
@RequiredArgsConstructor
public class SalesmanFitnessFunction implements FitnessFunction {

  private final SalesmanConditionData conditionData;

  /**
   * Calculates the fitness of a given individual.
   * @param individual the individual for which the fitness is to be calculated.
   * @return an integer representing the fitness of the individual.
   */
  @Override
  public int calculate(Individual individual) {
    int fitness = 0;
    int firstCity = 0;
    int lastCity = (int) individual.getChromosome()[individual.getChromosome().length - 1];
    int previousCity = firstCity;

    for (int gene = 0; gene < individual.getChromosome().length; gene++) {
      int currentCity = (int) individual.getChromosome()[gene];

      fitness += conditionData.roadMatrix()[previousCity][currentCity];

      previousCity = currentCity;
    }

    fitness += conditionData.roadMatrix()[lastCity][firstCity];

    return fitness;
  }

}
