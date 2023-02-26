package ip91.chui.oleh.algorithm.generationReplacement;

import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;

import java.util.List;

public interface GenerationReplacement {

  void process(Population population, List<Individual> offspring);

}
