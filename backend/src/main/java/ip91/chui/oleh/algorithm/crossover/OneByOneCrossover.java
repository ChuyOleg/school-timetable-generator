package ip91.chui.oleh.algorithm.crossover;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OneByOneCrossover implements Crossover {

  @Override
  public List<Individual> process(List<Individual> individuals) {
    List<Individual> offspring = new ArrayList<>();

    for (int indNum = 0; indNum < individuals.size(); indNum += 2) {
      Individual parent_1 = individuals.get(indNum);
      Individual parent_2 = individuals.get(indNum + 1);

      Individual individual1 = new Individual(new Object[parent_1.getChromosome().length]);
      Individual individual2 = new Individual(new Object[parent_1.getChromosome().length]);

      for (int geneNum = 0; geneNum < parent_1.getChromosome().length; geneNum++) {
        if (geneNum % 2 == 0) {
          setGeneFromParentToChild(parent_1, individual1, geneNum);
          setGeneFromParentToChild(parent_2, individual2, geneNum);
        } else {
          setGeneFromParentToChild(parent_2, individual1, geneNum);
          setGeneFromParentToChild(parent_1, individual2, geneNum);
        }
      }

      offspring.add(individual1);
      offspring.add(individual2);
    }

    return offspring;
  }

  private void setGeneFromParentToChild(Individual parent, Individual child, int geneNum) {
    GroupDto group = (GroupDto) parent.getChromosome()[geneNum];
    child.getChromosome()[geneNum] = GroupDto.builder()
        .id(group.getId())
        .gradeNumber(group.getGradeNumber())
        .shift(group.getShift())
        .lessons(group.getLessons()
            .stream()
            .map(l -> new LessonDto(
                l.getId(), group, l.getTeacherDto(), l.getSubjectDto(), l.getRoomDto(), l.getTimeSlotDto())
            )
            .collect(Collectors.toSet())
        )
        .build();
  }

}
