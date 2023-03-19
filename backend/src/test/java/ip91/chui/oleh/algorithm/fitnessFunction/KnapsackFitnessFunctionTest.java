package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.conditionData.KnapsackConditionData;
import ip91.chui.oleh.algorithm.model.Individual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KnapsackFitnessFunctionTest {

  private Individual aliveIndividual;
  private Individual deadIndividual;

  @Mock
  private KnapsackConditionData knapsackConditionData;
  @InjectMocks
  private KnapsackFitnessFunction fitnessFunction;

  @BeforeEach
  void init() {
    Object[] aliveChromosome = { true, false, true, true, false, false };
    Object[] deadChromosome = { true, true, true, true, false, true };

    int[] weightTable = {2, 4, 2, 3, 1, 2};
    int[] priceTable = {4, 5, 8, 2, 1, 6};
    int maxWeight = 10;

    when(knapsackConditionData.weightTable()).thenReturn(weightTable);
    when(knapsackConditionData.priceTable()).thenReturn(priceTable);
    when(knapsackConditionData.maxWeight()).thenReturn(maxWeight);

    aliveIndividual = new Individual(aliveChromosome);
    deadIndividual = new Individual(deadChromosome);
  }

  @Test
  void calculateShouldReturnCorrectFitnessForAliveIndividual() {
    int expectedResult = 14;
    int actualResult = fitnessFunction.calculate(aliveIndividual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void calculateShouldReturnCorrectFitnessForDeadIndividual() {
    int expectedResult = Integer.MIN_VALUE;
    int actualResult = fitnessFunction.calculate(deadIndividual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

}
