package ip91.chui.oleh.algorithm.crossover.chromosomeController;

import ip91.chui.oleh.algorithm.model.Individual;

/**
 * ChromosomeController is an interface that defines methods for manipulating chromosomes,
 * which are the building blocks of individuals in a population.
 *
 * @author Chui Oleh
 */
public interface ChromosomeController {

  /**
   * Creates a new chromosome using the point crossover technique.
   *
   * @param parent1 The first parent chromosome.
   * @param parent2 The second parent chromosome.
   * @param point The crossover point, which determines where the genetic information
   *        from the two parent chromosomes will be exchanged.
   * @return A new chromosome created using the point crossover technique.
   */
  Object[] createUsingPointCrossover(Individual parent1, Individual parent2, int point);

}
