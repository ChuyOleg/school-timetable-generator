package ip91.chui.oleh.algorithm.populationGenerator;

import ip91.chui.oleh.algorithm.model.Population;

/**
 The interface is used to generate a new population of individuals for an evolutionary algorithm.
 @author Chui Oleh
 */
public interface PopulationGenerator {

  Population generate();

}
