package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.fitnessFunction.step.FitnessFunctionStep;
import ip91.chui.oleh.algorithm.model.Individual;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimetableFitnessFunction implements FitnessFunction {

  private final List<FitnessFunctionStep> steps;

  @Override
  public int calculate(Individual individual) {
    var context = new FitnessFunctionContext();
    steps.forEach(step -> step.calculate(individual, context));
    return context.getFitnessScore();
  }
}
