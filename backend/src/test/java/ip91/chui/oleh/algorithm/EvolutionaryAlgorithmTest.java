package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.algorithm.crossover.Crossover;
import ip91.chui.oleh.algorithm.generationReplacement.GenerationReplacement;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import ip91.chui.oleh.algorithm.mutation.Mutation;
import ip91.chui.oleh.algorithm.populationGenerator.PopulationGenerator;
import ip91.chui.oleh.algorithm.selection.Selection;
import ip91.chui.oleh.model.dto.GroupDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class EvolutionaryAlgorithmTest {

  @Mock
  private PopulationGenerator populationGenerator;
  @Mock
  private Selection selection;
  @Mock
  private Crossover crossover;
  @Mock
  private Mutation mutation;
  @Mock
  private GenerationReplacement generationReplacement;
  @InjectMocks
  private EvolutionaryAlgorithm evolutionaryAlgorithm;

  @Test
  @Disabled
//  ToDo: 16/11/24 fix
  void Should_CallAllComponentsOfAlgorithm() {
    List<Individual> individuals = new ArrayList<>();

    GroupDto group_1 = new GroupDto();
    group_1.setLessons(new HashSet<>());
    GroupDto group_2 = new GroupDto();
    group_2.setLessons(new HashSet<>());
    GroupDto group_3 = new GroupDto();
    group_3.setLessons(new HashSet<>());
    GroupDto group_4 = new GroupDto();
    group_4.setLessons(new HashSet<>());

    Object[] chromosome_1 = { group_1, group_2 };
    Object[] chromosome_2 = { group_3, group_4 };

    individuals.add(new Individual(chromosome_1, 100));
    individuals.add(new Individual(chromosome_2, 50));
    Population population = new Population(individuals);

    when(populationGenerator.generate(any())).thenReturn(population);

    evolutionaryAlgorithm.generate();
    verify(populationGenerator).generate(any());
    verify(selection).process(any());
    verify(crossover).process(any());
    verify(mutation).process(any(), any(), any());
    verify(generationReplacement).process(any(), any());
  }
}
