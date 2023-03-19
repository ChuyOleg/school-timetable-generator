package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.conditionData.SalesmanConditionData;
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
class SalesmanFitnessFunctionTest {

  private Individual individual;

  @Mock
  private SalesmanConditionData salesmanConditionData;
  @InjectMocks
  private SalesmanFitnessFunction fitnessFunction;

  @BeforeEach
  void init() {
    int[][] roadMatrix = {
        {Integer.MAX_VALUE, 3, 5, 9},
        {3, Integer.MAX_VALUE, 4, 2},
        {5, 4, Integer.MAX_VALUE, 7},
        {9, 2, 7, Integer.MAX_VALUE}
    };

    when(salesmanConditionData.roadMatrix()).thenReturn(roadMatrix);
  }

  @Test
  void calculateOneShouldReturnCorrectFitness() {
    Object[] chromosome = { 2, 3, 1 };
    individual = new Individual(chromosome);

    int expectedResult = 17;
    int actualResult = fitnessFunction.calculate(individual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void calculateOneShouldReturnCorrectFitness_2() {
    Object[] chromosome = { 1, 2, 3 };
    individual = new Individual(chromosome);

    int expectedResult = 23;
    int actualResult = fitnessFunction.calculate(individual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void calculateOneShouldReturnCorrectFitness_3() {
    Object[] chromosome = { 3, 2, 1 };
    individual = new Individual(chromosome);

    int expectedResult = 23;
    int actualResult = fitnessFunction.calculate(individual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

}
