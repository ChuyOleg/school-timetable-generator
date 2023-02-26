package ip91.chui.oleh.algorithm.crossover;

import ip91.chui.oleh.algorithm.model.Individual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomPointCrossoverTest {

  private final Random random = mock(Random.class, withSettings().withoutAnnotations());
  @InjectMocks
  private RandomPointCrossover randomPointCrossover;

  private final Object[] chromosome = { true, true, false, true, false, false, true, false };

  @Test
  void getPointShouldReturnCorrectPoint_1() {
    Individual individual = new Individual(chromosome);

    when(random.nextInt(anyInt())).thenReturn(0);

    int expectedResult = 1;
    int actualResult = randomPointCrossover.getPoint(individual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void getPointShouldReturnCorrectPoint_2() {
    Individual individual = new Individual(chromosome);

    when(random.nextInt(anyInt())).thenReturn(3);

    int expectedResult = 4;
    int actualResult = randomPointCrossover.getPoint(individual);

    Assertions.assertEquals(expectedResult, actualResult);
  }

}
