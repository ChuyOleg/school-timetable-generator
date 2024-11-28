package ip91.chui.oleh.algorithm.util;

import ip91.chui.oleh.algorithm.model.Result;
import ip91.chui.oleh.algorithm.model.RuntimeInfo;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class LoggerUtils {

  private static final String TRANSIENT_RESULT_TEMPLATE = "Generation: {}. Best fitnessScore: {}";

  private LoggerUtils() {}

  public static void logUltimateResult(Result result) {
    final int generation = result.getGeneration();
    final int fitnessScore = result.getBestIndividual().getFitness();

    log.info("------------------------------------");
    log.info("Generation: {}", generation);
    log.info("Score: {}", fitnessScore);
  }

  public static void logTransientResult(int generationCounter, RuntimeInfo runtimeInfo) {
    if (generationCounter % 250 == 0) {
      log.info(
          TRANSIENT_RESULT_TEMPLATE,
          generationCounter, runtimeInfo.getBestIndividual().getFitness());
    }
  }
}
