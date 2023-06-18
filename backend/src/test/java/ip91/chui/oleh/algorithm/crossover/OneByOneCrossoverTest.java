package ip91.chui.oleh.algorithm.crossover;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class OneByOneCrossoverTest {

  private static Long nextGroupId = 1L;

  private List<GroupDto> groups_A;
  private List<GroupDto> groups_B;
  private List<GroupDto> groups_C;
  private List<GroupDto> groups_D;
  private List<Individual> parents_1;
  private List<Individual> parents_2;

  private OneByOneCrossover oneByOneCrossover;

  @BeforeEach
  void init() {
    groups_A = createGroups();
    groups_B = createGroups();
    groups_C = createGroups();
    groups_D = createGroups();

    Individual parent_1 = new Individual(groups_A.toArray());
    Individual parent_2 = new Individual(groups_B.toArray());
    Individual parent_3 = new Individual(groups_C.toArray());
    Individual parent_4 = new Individual(groups_D.toArray());

    parents_1 = Arrays.asList(parent_1, parent_2);
    parents_2 = Arrays.asList(parent_1, parent_2, parent_3, parent_4);
    oneByOneCrossover = new OneByOneCrossover();
  }

  @Test
  void Should_ReturnCorrectOffSpring1_WhenCrossoverParents1() {
    Object[] chromosome_1 = {
        groups_A.get(0), groups_B.get(1), groups_A.get(2), groups_B.get(3), groups_A.get(4), groups_B.get(5)
    };
    Object[] chromosome_2 = {
        groups_B.get(0), groups_A.get(1), groups_B.get(2), groups_A.get(3), groups_B.get(4), groups_A.get(5)
    };

    List<Individual> expectedOffspring = Arrays.asList(
        new Individual(chromosome_1),
        new Individual(chromosome_2)
    );

    List<Individual> actualOffspring = oneByOneCrossover.process(parents_1);

    for (int index = 0; index < actualOffspring.size(); index++) {
      assertArrayEquals(
          expectedOffspring.get(index).getChromosome(), actualOffspring.get(index).getChromosome()
      );
    }
  }

  @Test
  void Should_ReturnCorrectOffSpring_1() {
    Object[] chromosome_1 = {
        groups_A.get(0), groups_B.get(1), groups_A.get(2), groups_B.get(3), groups_A.get(4), groups_B.get(5)
    };
    Object[] chromosome_2 = {
        groups_B.get(0), groups_A.get(1), groups_B.get(2), groups_A.get(3), groups_B.get(4), groups_A.get(5)
    };
    Object[] chromosome_3 = {
        groups_C.get(0), groups_D.get(1), groups_C.get(2), groups_D.get(3), groups_C.get(4), groups_D.get(5)
    };
    Object[] chromosome_4 = {
        groups_D.get(0), groups_C.get(1), groups_D.get(2), groups_C.get(3), groups_D.get(4), groups_C.get(5)
    };

    List<Individual> expectedOffspring = Arrays.asList(
        new Individual(chromosome_1),
        new Individual(chromosome_2),
        new Individual(chromosome_3),
        new Individual(chromosome_4)
    );

    List<Individual> actualOffspring = oneByOneCrossover.process(parents_2);

    for (int index = 0; index < actualOffspring.size(); index++) {
      assertArrayEquals(
          expectedOffspring.get(index).getChromosome(), actualOffspring.get(index).getChromosome()
      );
    }
  }

  private List<GroupDto> createGroups() {
    List<GroupDto> groups = new ArrayList<>();

    for (int gradeNumber = 1; gradeNumber < 7; gradeNumber++) {
      groups.add(GroupDto.builder()
          .id(nextGroupId++)
          .gradeNumber(gradeNumber)
          .lessons(new HashSet<>())
          .build()
      );
    }

    return groups;
  }

}
