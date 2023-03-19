package ip91.chui.oleh.algorithm.selection;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HalfPopulationSelectionTest {

  @InjectMocks
  private HalfPopulationSelection selection;

  private Population population;
  private Individual individualFitness_1;
  private Individual individualFitness_2;
  private Individual individualFitness_3;
  private Individual individualFitness_4;
  private Individual individualFitness_5;

  @BeforeEach
  void init() {
    individualFitness_1 = new Individual(null, 1);
    individualFitness_2 = new Individual(null, 2);
    individualFitness_3 = new Individual(null, 3);
    individualFitness_4 = new Individual(null, 4);
    individualFitness_5 = new Individual(null, 5);
  }

  @Test
  void processBackpackPopulationWithFourIndividualShouldReturnCorrectSelection() {
    List<Individual> individuals = new ArrayList<>(Arrays.asList(
        individualFitness_1, individualFitness_3, individualFitness_2, individualFitness_4)
    );
    individuals.sort(getMaximizationComparator());
    population = new Population(individuals);

    List<Individual> expectedResult = Arrays.asList(individualFitness_3, individualFitness_4);
    List<Individual> actualResult = selection.process(population);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void processBackpackPopulationWithFiveIndividualShouldReturnCorrectSelection() {
    List<Individual> individuals = new ArrayList<>(Arrays.asList(
        individualFitness_5, individualFitness_1, individualFitness_3, individualFitness_2, individualFitness_4)
    );
    individuals.sort(getMaximizationComparator());
    population = new Population(individuals);

    List<Individual> expectedResult = Arrays.asList(individualFitness_4, individualFitness_5);
    List<Individual> actualResult = selection.process(population);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void processSalesmanPopulationWithFourIndividualShouldReturnCorrectSelection() {
    List<Individual> individuals = new ArrayList<>(Arrays.asList(
        individualFitness_1, individualFitness_3, individualFitness_2, individualFitness_4)
    );
    individuals.sort(getMinimizationComparator());
    population = new Population(individuals);

    List<Individual> expectedResult = Arrays.asList(individualFitness_2, individualFitness_1);
    List<Individual> actualResult = selection.process(population);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void processSalesmanPopulationWithFiveIndividualShouldReturnCorrectSelection() {
    List<Individual> individuals = new ArrayList<>(Arrays.asList(
        individualFitness_5, individualFitness_1, individualFitness_3, individualFitness_2, individualFitness_4)
    );
    individuals.sort(getMinimizationComparator());
    population = new Population(individuals);

    List<Individual> expectedResult = Arrays.asList(individualFitness_2, individualFitness_1);
    List<Individual> actualResult = selection.process(population);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  private Comparator<Individual> getMaximizationComparator() {
    return Comparator.comparingInt(Individual::getFitness);
  }

  private Comparator<Individual> getMinimizationComparator() {
    return (ch1, ch2) -> ch2.getFitness() - ch1.getFitness();
  }

}
