package ip91.chui.oleh.algorithm.crossover.chromosomeController;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimetableChromosomeController implements ChromosomeController {

  @Override
  public Object[] createUsingPointCrossover(Individual parent1, Individual parent2, int point) {
    List<Object> child = new ArrayList<>();

    List<Object> parent1Part = Arrays.asList(parent1.getChromosome()).subList(0, point);
    List<Object> parent2Part = Arrays.asList(parent2.getChromosome()).subList(point, parent2.getChromosome().length);

    List<Object> firstPart = createPartOfChild(parent1Part);
    List<Object> secondPart = createPartOfChild(parent2Part);

    child.addAll(firstPart);
    child.addAll(secondPart);

    return child.toArray();
  }

  private List<Object> createPartOfChild(List<Object> parentGenes) {
    return parentGenes.stream().map(gene -> {
      GroupDto group = (GroupDto) gene;
      return GroupDto.builder()
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
    }).collect(Collectors.toList());
  }

}
