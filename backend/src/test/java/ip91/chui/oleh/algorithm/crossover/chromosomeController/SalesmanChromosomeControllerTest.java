package ip91.chui.oleh.algorithm.crossover.chromosomeController;

import ip91.chui.oleh.algorithm.model.Individual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanChromosomeControllerTest {

  private final ChromosomeController chromosomeController = new SalesmanChromosomeController();

  private Individual parent1;
  private Individual parent2;

  @BeforeEach
  void init() {
    Object[] parent1Chromosome = { 2, 0, 3, 5, 1, 6, 7, 4 };
    Object[] parent2Chromosome = { 5, 2, 6, 7, 0, 4, 3, 1 };

    parent1 = new Individual(parent1Chromosome);
    parent2 = new Individual(parent2Chromosome);
  }

  @Test
  void createUsingPointCrossoverShouldReturnCorrectChildren_1() {
    final int point = 1;

    Object[] expectedChromosome = { 2, 5, 6, 7, 0, 4, 3, 1 };

    Object[] actualChromosome = chromosomeController.createUsingPointCrossover(parent1, parent2, point);

    assertArrayEquals(expectedChromosome, actualChromosome);

    expectedChromosome = new Object[]{ 5, 0, 3, 2, 1, 6, 7, 4 };

    actualChromosome = chromosomeController.createUsingPointCrossover(parent2, parent1, point);

    assertArrayEquals(expectedChromosome, actualChromosome);
  }

  @Test
  void createUsingPointCrossoverShouldReturnCorrectChildren_2() {
    final int point = 7;

    Object[] expectedChromosome = { 2, 0, 3, 5, 1, 6, 7, 4 };

    Object[] actualChromosome = chromosomeController.createUsingPointCrossover(parent1, parent2, point);

    assertArrayEquals(expectedChromosome, actualChromosome);

    expectedChromosome = new Object[]{ 5, 2, 6, 7, 0, 4, 3, 1 };

    actualChromosome = chromosomeController.createUsingPointCrossover(parent2, parent1, point);

    assertArrayEquals(expectedChromosome, actualChromosome);
  }

  @Test
  void createUsingPointCrossoverShouldReturnCorrectChildren_3() {
    final int point = 3;

    Object[] expectedChromosome = { 2, 0, 3, 7, 5, 4, 6, 1 };

    Object[] actualChromosome = chromosomeController.createUsingPointCrossover(parent1, parent2, point);

    assertArrayEquals(expectedChromosome, actualChromosome);

    expectedChromosome = new Object[]{ 5, 2, 6, 0, 1, 3, 7, 4 };

    actualChromosome = chromosomeController.createUsingPointCrossover(parent2, parent1, point);

    assertArrayEquals(expectedChromosome, actualChromosome);
  }

}
