package ip91.chui.oleh.algorithm.crossover.chromosomeController;

import ip91.chui.oleh.algorithm.model.Individual;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TimetableChromosomeController implements ChromosomeController {

  @Override
  public Object[] createUsingPointCrossover(Individual parent1, Individual parent2, int point) {
    List<Object> child = new ArrayList<>();
    List<Object> firstPart = Arrays.asList(parent1.getChromosome()).subList(0, point);
    List<Object> secondPart = Arrays.asList(parent2.getChromosome()).subList(point, parent2.getChromosome().length);

    child.addAll(firstPart);
    child.addAll(secondPart);

    return child.toArray();
  }

}
