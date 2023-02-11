package ip91.chui.oleh.algorithm.selection;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;

import java.util.List;

/**
 * The interface provides a mechanism for processing a population of individuals in order
 * to select a subset of individuals for further genetic operations.
 * @author Chui Oleh
 */
public interface Selection {

  /**
   * Processes the given population and returns a list of individuals that have been selected for further genetic operations.
   * @param population the population to be processed
   * @return a list of selected individuals
   */
  List<Individual> process(Population population);

}
