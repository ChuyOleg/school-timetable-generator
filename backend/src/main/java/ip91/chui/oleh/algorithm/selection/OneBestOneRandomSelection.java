package ip91.chui.oleh.algorithm.selection;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The class performs selection by choosing the best individual and a random individual from the population.
 * @author Chui Oleh
 */
@Component
@RequiredArgsConstructor
public class OneBestOneRandomSelection implements Selection {

  private final Random random;

  /**
   * The method selects the best individual and a random individual from the population.
   * @param population a population of individuals
   * @return a list of two individuals, the best individual and a random individual
   */
  @Override
  public List<Individual> process(Population population) {
    int randomNum = random.nextInt(population.individuals().size() - 1);

    Individual theBestIndividual = population.individuals().get(population.individuals().size() - 1);
    Individual randomIndividual = population.individuals().get(randomNum);

    return Arrays.asList(randomIndividual, theBestIndividual);
  }

}
