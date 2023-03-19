package ip91.chui.oleh.algorithm.crossover;

import ip91.chui.oleh.algorithm.model.Individual;

import java.util.List;

/**
 * The interface Crossover defines a process for producing new individuals from a given list of individuals.
 * @author Chui Oleh
 */
public interface Crossover {

  /**
   * This method takes a list of individuals as input and returns a new list of individuals,
   * produced through a crossover process.
   * @param individuals The input list of individuals.
   * @return A new list of individuals, produced through a crossover process.
   */
  List<Individual> process(List<Individual> individuals);

}
