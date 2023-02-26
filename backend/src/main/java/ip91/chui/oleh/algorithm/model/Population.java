package ip91.chui.oleh.algorithm.model;

import java.util.List;
import java.util.stream.Collectors;

public record Population(List<Individual> individuals) {

  @Override
  public String toString() {
    return individuals.stream()
        .map(Individual::toString)
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
