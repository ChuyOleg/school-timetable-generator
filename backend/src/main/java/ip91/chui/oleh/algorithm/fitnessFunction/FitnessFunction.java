package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.RuntimeInfo;
import java.util.List;

/**
 * An interface for defining fitness functions used in evolutionary algorithms.
 * @author Chui Oleh
 */
public interface FitnessFunction {

  /**
   * Calculates the fitness value of a given individual.
   * @param individual the individual whose fitness value will be calculated
   * @return the fitness value of the individual
   */
  int calculate(Individual individual);

  int calculate(Individual individual, RuntimeInfo runtimeInfo);

  void calculate(List<Individual> individuals, RuntimeInfo runtimeInfo);
}
