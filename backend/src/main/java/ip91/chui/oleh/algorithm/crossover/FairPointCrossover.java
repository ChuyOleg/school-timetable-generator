package ip91.chui.oleh.algorithm.crossover;

import ip91.chui.oleh.algorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.algorithm.model.Individual;

public class FairPointCrossover extends AbstractPointCrossover {

  public FairPointCrossover(ChromosomeController chromosomeController) {
    super(chromosomeController);
  }

  @Override
  protected int getPoint(Individual individual) {
    return individual.getChromosome().length / 2;
  }

}
