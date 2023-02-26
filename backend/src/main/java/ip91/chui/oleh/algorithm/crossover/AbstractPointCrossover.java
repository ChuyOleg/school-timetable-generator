package ip91.chui.oleh.algorithm.crossover;

import ip91.chui.oleh.algorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.algorithm.model.Individual;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractPointCrossover is an abstract class that implements the {@link Crossover} interface.
 * This class defines the basic structure for point crossover operations, allowing to apply this technique in different problem domains.
 * The point crossover operation consists in taking a random point of the chromosome, and then swapping the genes of both parents according to this point.
 *
 * @author Chui Oleh
 */
@RequiredArgsConstructor
public abstract class AbstractPointCrossover implements Crossover {

  private final ChromosomeController chromosomeController;

  /**
   * Applies the point crossover operation to a given list of individuals.
   * The number of offspring generated will be equal to the number of individuals in the input list.
   *
   * @param individuals the list of individuals to apply the crossover operation.
   * @return the list of offspring generated from the crossover operation.
   */
  @Override
  public List<Individual> process(List<Individual> individuals) {
    List<Individual> offspring = new ArrayList<>();

    for (int indNum = 0; indNum < individuals.size(); indNum += 2) {
      Individual parent_1 = individuals.get(indNum);
      Individual parent_2 = individuals.get(indNum + 1);

      final int point = getPoint(parent_1);

      Object[] child_1Chromosome = chromosomeController.createUsingPointCrossover(parent_1, parent_2, point);
      Object[] child_2Chromosome = chromosomeController.createUsingPointCrossover(parent_2, parent_1, point);

      Individual individual_1 = new Individual(child_1Chromosome);
      Individual individual_2 = new Individual(child_2Chromosome);

      offspring.add(individual_1);
      offspring.add(individual_2);
    }

    return offspring;
  }

  /**
   * Returns the point to be used in the point crossover operation.
   *
   * @param individual the individual to get the point from.
   * @return the point to be used in the crossover operation.
   */
  protected abstract int getPoint(Individual individual);

}
