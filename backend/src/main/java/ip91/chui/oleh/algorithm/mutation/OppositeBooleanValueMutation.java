package ip91.chui.oleh.algorithm.mutation;

import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.model.Individual;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class OppositeBooleanValueMutation implements Mutation {

  private final FitnessFunction fitnessFunction;
  private final Random random;

  @Override
  public void process(List<Individual> individuals) {
    individuals.forEach(individual -> {
      for (int geneNum = 0; geneNum < individual.getChromosome().length; geneNum++) {
        if (shouldMutate()) {
          boolean geneValue = (boolean) individual.getChromosome()[geneNum];
          individual.getChromosome()[geneNum] = !geneValue;
        }
      }

      individual.setFitness(fitnessFunction.calculate(individual));
    });
  }

  private boolean shouldMutate() {
    return random.nextInt(Config.MUTATION_MEASURE) < Config.MUTATION_PERCENTAGE;
  }

}
