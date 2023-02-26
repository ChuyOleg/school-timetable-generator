package ip91.chui.oleh.algorithm.selection;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;

import java.util.Arrays;
import java.util.List;

/**
 * The class performs selection by choosing the best two individuals from the population.
 * @author Chui Oleh
 */
public class TwoBestSelection implements Selection {

  /**
   * The method process selects the two best individuals from the given population and returns
   * them as a list of individuals.
   * @param population The population from which the two best individuals are selected.
   * @return A list containing the two best individuals from the given population.
   */
  @Override
  public List<Individual> process(Population population) {
    Individual theBestIndividual = population.individuals().get(population.individuals().size() - 1);
    Individual secondBestIndividual = population.individuals().get(population.individuals().size() - 2);

    return Arrays.asList(secondBestIndividual, theBestIndividual);
  }

}
