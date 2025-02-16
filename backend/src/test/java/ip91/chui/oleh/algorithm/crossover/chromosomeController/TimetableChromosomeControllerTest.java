package ip91.chui.oleh.algorithm.crossover.chromosomeController;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableChromosomeControllerTest {

  private static Long nextGroupId = 1L;

  private List<GroupDto> timetable_1;
  private List<GroupDto> timetable_2;
  private List<Individual> parents_1;

  private TimetableChromosomeController timetableChromosomeController;

  @BeforeEach
  void init() {
    timetable_1 = createGroups();
    timetable_2 = createGroups();

    Individual parent_1 = new Individual(timetable_1.toArray());
    Individual parent_2 = new Individual(timetable_2.toArray());

    parents_1 = Arrays.asList(parent_1, parent_2);
    timetableChromosomeController = new TimetableChromosomeController();
  }

  @Test
  void Should_ReturnCorrectChromosome_WhenPointIs1() {
    Object[] expectedChromosome = {
        timetable_1.get(0), timetable_2.get(1), timetable_2.get(2), timetable_2.get(3), timetable_2.get(4), timetable_2.get(5)
    };

    Object[] actualChromosome = timetableChromosomeController.createUsingPointCrossover(parents_1.get(0), parents_1.get(1), 1);

    assertArrayEquals(expectedChromosome, actualChromosome);
  }

  @Test
  void Should_ReturnCorrectChromosome_WhenPointIs3() {
    Object[] expectedChromosome = {
        timetable_1.get(0), timetable_1.get(1), timetable_1.get(2), timetable_2.get(3), timetable_2.get(4), timetable_2.get(5)
    };

    Object[] actualChromosome = timetableChromosomeController.createUsingPointCrossover(parents_1.get(0), parents_1.get(1), 3);

    assertArrayEquals(expectedChromosome, actualChromosome);
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