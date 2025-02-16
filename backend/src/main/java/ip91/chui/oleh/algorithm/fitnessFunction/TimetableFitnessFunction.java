package ip91.chui.oleh.algorithm.fitnessFunction;

import ip91.chui.oleh.algorithm.StaticContext;
import ip91.chui.oleh.algorithm.fitnessFunction.step.FitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.LessonsExtractor;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.SecondShiftLessonNumbersTransformer;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.RuntimeInfo;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.repository.ShiftsIntersectionRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimetableFitnessFunction implements FitnessFunction {

  private static final double WEAK_INDIVIDUAL_THRESHOLD_MULTIPLIER = 1.1;

  private final List<FitnessFunctionStep> steps;
  private final SecondShiftLessonNumbersTransformer transformer;

  private final AuthenticationService authService;
  private final ShiftsIntersectionRepository shiftsRepository;

  @Override
  public int calculate(Individual individual) {
    return calculateInner(individual, null);
  }

  @Override
  public int calculate(Individual individual, RuntimeInfo runtimeInfo) {
    return calculateInner(individual, runtimeInfo.getBestIndividual().getFitness());
  }

  @Override
  public void calculate(List<Individual> individuals, RuntimeInfo runtimeInfo) {
    individuals.forEach(individual -> individual.setFitness(calculate(individual, runtimeInfo)));
  }

  private int calculateInner(Individual individual, Integer bestFitnessScoreFromPreviousGeneration){
    User user = authService.extractPrincipalFromSecurityContextHolder();
    var shiftsIntersections = shiftsRepository.findAllByUserId(user.getId());

    List<LessonDto> lessons = LessonsExtractor.fromIndividual(individual);
    transformer.transform(lessons, shiftsIntersections);

    var context = new FitnessFunctionContext(lessons, bestFitnessScoreFromPreviousGeneration);

    for (var step : steps) {
      if (isIndividualWeak(context, bestFitnessScoreFromPreviousGeneration)) {
        StaticContext.incSkippedFitnessFunctionStepsCount();
        context.setFitnessScore(Integer.MIN_VALUE);
        break;
      } else {
        StaticContext.incExecutedFitnessFunctionStepsCount();
        step.calculate(individual, context);
      }
    }

    if (isIndividualWeak(context, bestFitnessScoreFromPreviousGeneration)) {
      context.setFitnessScore(Integer.MIN_VALUE);
    }

    transformer.rollbackTransformation(lessons, shiftsIntersections);

    return context.getFitnessScore();
  }

  private boolean isIndividualWeak(
      FitnessFunctionContext functionContext, Integer bestFitnessScore) {

    if (Objects.isNull(bestFitnessScore)) {
      return false;
    }

    double weakIndividualThreshold = bestFitnessScore * WEAK_INDIVIDUAL_THRESHOLD_MULTIPLIER;
    return functionContext.getFitnessScore() > weakIndividualThreshold;
  }
}
