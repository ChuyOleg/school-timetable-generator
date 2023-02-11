package ip91.chui.oleh.algorithm.selection;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OneBestOneRandomSelectionTest {

  private final Random random = mock(Random.class, withSettings().withoutAnnotations());
  @InjectMocks
  private OneBestOneRandomSelection selection;

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
  void processKnapsackPopulationShouldReturnCorrectSelection() {
    List<Individual> individuals = new ArrayList<>(Arrays.asList(
        individualFitness_1, individualFitness_3, individualFitness_2, individualFitness_4, individualFitness_5)
    );
    individuals.sort(getMaximizationComparator());
    population = new Population(individuals);

    when(random.nextInt(population.individuals().size() - 1)).thenReturn(2);

    List<Individual> expectedResult = Arrays.asList(individualFitness_3, individualFitness_5);
    List<Individual> actualResult = selection.process(population);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void processSalesmanPopulationShouldReturnCorrectSelection() {
    List<Individual> individuals = new ArrayList<>(Arrays.asList(
        individualFitness_1, individualFitness_3, individualFitness_2, individualFitness_4, individualFitness_5)
    );
    individuals.sort(getMinimizationComparator());
    population = new Population(individuals);

    when(random.nextInt(population.individuals().size() - 1)).thenReturn(3);

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
