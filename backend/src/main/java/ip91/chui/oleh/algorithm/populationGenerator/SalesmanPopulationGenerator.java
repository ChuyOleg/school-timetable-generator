package ip91.chui.oleh.algorithm.populationGenerator;

import ip91.chui.oleh.algorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The class generates a population of individuals for the Traveling Salesman Problem.
 * @author Chui Oleh
 */
@AllArgsConstructor
@Getter
public class SalesmanPopulationGenerator implements PopulationGenerator {

  private final SalesmanConditionData conditionData;
  private final Random random;

  /**
   * Generates a population of individuals for the Traveling Salesman Problem.
   * @return a population of individuals for the Traveling Salesman Problem.
   */
  @Override
  public Population generate() {
    List<Individual> individuals = new ArrayList<>(Config.POPULATION_SIZE);

    for (int individualNum = 0; individualNum < Config.POPULATION_SIZE; individualNum++) {
      Object[] chromosome = new Object[conditionData.roadMatrix().length - 1];
      List<Integer> freeCity = IntStream.range(1, conditionData.roadMatrix().length).boxed().collect(Collectors.toList());
      int fitness = 0;
      int previousCity = 0;

      for (int gene = 0; gene < conditionData.roadMatrix().length - 1; gene++) {
        int randomCityIndex = random.nextInt(freeCity.size());
        int randomCity = freeCity.get(randomCityIndex);
        chromosome[gene] = randomCity;
        fitness += conditionData.roadMatrix()[previousCity][randomCity];
        previousCity = randomCity;
        freeCity.remove(randomCityIndex);
      }
      fitness += conditionData.roadMatrix()[previousCity][0];

      Individual individual = new Individual(chromosome);
      individual.setFitness(fitness);
      individuals.add(individual);
    }

    return new Population(individuals);
  }
}
