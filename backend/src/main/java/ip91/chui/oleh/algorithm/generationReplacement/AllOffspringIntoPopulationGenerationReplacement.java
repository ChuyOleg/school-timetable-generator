package ip91.chui.oleh.algorithm.generationReplacement;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;

import java.util.ArrayList;
import java.util.List;

public class AllOffspringIntoPopulationGenerationReplacement implements GenerationReplacement {

  @Override
  public void process(Population population, List<Individual> offspring) {
    List<Individual> haveToBeDeleted = new ArrayList<>();
    List<Individual> haveToBeAdded = new ArrayList<>();

    int offSpringIndex = 0;

    outerLoop:
    for (Individual individual : population.individuals()) {
      while (true) {
        if (offSpringIndex >= offspring.size()) {
          break outerLoop;
        }
        Individual child = offspring.get(offSpringIndex);
        if (isDead(child)) {
          offSpringIndex++;
          continue;
        }

        haveToBeDeleted.add(individual);
        haveToBeAdded.add(child);
        offSpringIndex++;
        break;
      }
    }

    haveToBeDeleted.forEach(population.individuals()::remove);
    haveToBeAdded.forEach(population.individuals()::add);
  }

  private boolean isDead(Individual individual) {
    return individual.getFitness() == Integer.MIN_VALUE;
  }

}
