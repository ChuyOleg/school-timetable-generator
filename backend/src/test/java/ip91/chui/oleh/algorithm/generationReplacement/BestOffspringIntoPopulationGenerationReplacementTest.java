package ip91.chui.oleh.algorithm.generationReplacement;

import static org.assertj.core.api.Assertions.assertThat;

import ip91.chui.oleh.IndividualFactoryUtil;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class BestOffspringIntoPopulationGenerationReplacementTest {

  private static final int GROUPS_COUNT = 1;
  private static final int LESSONS_COUNT = 5;

  private GenerationReplacement generationReplacement;

  @BeforeEach
  void setUp() {
    this.generationReplacement = new BestOffspringIntoPopulationGenerationReplacement();
  }

  @Test
  void shouldProperlyReplaceGeneration() {
    var populationIndividual1 = individual(10);
    var populationIndividual2 = individual(20);
    var populationIndividual3 = individual(30);
    Population population = new Population(new ArrayList<>(List.of(
        populationIndividual1, populationIndividual2, populationIndividual3)));

    var offspringIndividual1 = individual(5);
    var offspringIndividual2 = individual(15);
    var offspringIndividual3 = individual(25);
    List<Individual> offspring = new ArrayList<>(List.of(
        offspringIndividual1, offspringIndividual2, offspringIndividual3));

    generationReplacement.process(population, offspring);

    assertThat(population.individuals())
        .containsExactlyInAnyOrder(populationIndividual1, offspringIndividual1, offspringIndividual2);
  }

  private Individual individual(int fitnessScore) {
    var individual = IndividualFactoryUtil
        .individualWithUniqueSubjectIdsPerGroup(GROUPS_COUNT, LESSONS_COUNT);

    individual.setFitness(fitnessScore);
    return individual;
  }
}