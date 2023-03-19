package ip91.chui.oleh.algorithm.mutation;

import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.model.Individual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OppositeBooleanValueMutationTest {

  @Mock
  private FitnessFunction fitnessFunction;
  private final Random random = mock(Random.class, withSettings().withoutAnnotations());
  @InjectMocks
  private OppositeBooleanValueMutation mutation;

  private List<Individual> offspring;

  @BeforeEach
  void init() {
    Individual child1 = new Individual(new Object[]{ true, false, false, true });
    Individual child2 = new Individual(new Object[]{ false, false, true, false });

    offspring = Arrays.asList(child1, child2);
  }

  @Test
  void processShouldChangeIndividuals() {
    when(random.nextInt(anyInt())).thenReturn(Config.MUTATION_PERCENTAGE - 1);

    mutation.process(offspring);

    Object[] expectedChromosome1 = { false, true, true, false };

    assertArrayEquals(expectedChromosome1, offspring.get(0).getChromosome());

    Object[] expectedChromosome2 = { true, true, false, true };

    assertArrayEquals(expectedChromosome2, offspring.get(1).getChromosome());
  }

  @Test
  void processShouldNotChangeIndividuals() {
    when(random.nextInt(anyInt())).thenReturn(Config.MUTATION_PERCENTAGE + 1);

    mutation.process(offspring);

    Object[] expectedChromosome1 = { true, false, false, true };

    assertArrayEquals(expectedChromosome1, offspring.get(0).getChromosome());

    Object[] expectedChromosome2 = { false, false, true, false };

    assertArrayEquals(expectedChromosome2, offspring.get(1).getChromosome());
  }

}
