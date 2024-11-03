package ip91.chui.oleh.algorithm.fitnessFunction.step;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunctionContext;
import ip91.chui.oleh.algorithm.model.Individual;

public interface FitnessFunctionStep {

  void calculate(Individual individual, FitnessFunctionContext context);
}
