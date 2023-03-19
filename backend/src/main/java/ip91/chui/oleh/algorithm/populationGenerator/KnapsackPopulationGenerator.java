package ip91.chui.oleh.algorithm.populationGenerator;

import ip91.chui.oleh.algorithm.conditionData.KnapsackConditionData;
import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class generates a population of individuals for a knapsack problem.
 * @author Chui Oleh
 */
@RequiredArgsConstructor
@Getter
public class KnapsackPopulationGenerator implements PopulationGenerator {

  private final KnapsackConditionData conditionData;
  private final Random random;

  /**
   * Generates a population of individuals for the knapsack problem.
   * @return A population of individuals.
   */
  @Override
  public Population generate() {
    List<Individual> individuals = new ArrayList<>(Config.POPULATION_SIZE);

    for (int individualNum = 0; individualNum < Config.POPULATION_SIZE; individualNum++) {
      Object[] chromosome = new Object[conditionData.weightTable().length];
      int knapsackCurrentWeight = 0;
      int price = 0;

      for (int gene = 0; gene < conditionData.weightTable().length; gene++) {
        boolean chance = random.nextInt(2) == 0;
        int knapsackNewWeight = knapsackCurrentWeight + conditionData.weightTable()[gene];

        if (chance && knapsackNewWeight <= conditionData.maxWeight()) {
          chromosome[gene] = true;
          knapsackCurrentWeight = knapsackNewWeight;
          price += conditionData.priceTable()[gene];
        } else {
          chromosome[gene] = false;
        }
      }
      Individual individual = new Individual(chromosome);
      individual.setFitness(price);
      individuals.add(individual);
    }

    return new Population(individuals);
  }

}
