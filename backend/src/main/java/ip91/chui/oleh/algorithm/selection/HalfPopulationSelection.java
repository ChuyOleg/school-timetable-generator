package ip91.chui.oleh.algorithm.selection;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The class provides a method to select better half (thanks to the presorting) of the individuals in a population.
 * @author Chui Oleh
 */
@Component
public class HalfPopulationSelection implements Selection {

  /**
   * This method processes the given population by selecting half of its individuals.
   * The individuals are selected randomly and sorted based on their fitness.
   * If the number of selected individuals is odd, one individual is removed.
   * @param population The population to be processed.
   * @return A list of selected individuals.
   */
  @Override
  public List<Individual> process(Population population) {
    List<Individual> sample = population.individuals().stream()
        .skip(population.individuals().size() / 2)
        .collect(Collectors.toList());

    if (sample.size() % 2 == 1) {
      sample.remove(0);
    }

    return sample;
  }

}
