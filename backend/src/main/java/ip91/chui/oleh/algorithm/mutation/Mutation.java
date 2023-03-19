package ip91.chui.oleh.algorithm.mutation;

import ip91.chui.oleh.algorithm.model.Individual;

import java.util.List;

/**
 * The Mutation interface is responsible for defining the behavior of mutation process in evolutionary algorithms.
 * A mutation is a process that introduces a random change in the solution space.
 *
 * @author Chui Oleh
 */
public interface Mutation {

  /**
   * Process the mutation operation on a list of individuals.
   *
   * @param individuals A list of individuals to be processed by mutation.
   */
  void process(List<Individual> individuals);

}
