package ip91.chui.oleh.algorithm.util;

import com.newrelic.api.agent.NewRelic;
import ip91.chui.oleh.algorithm.StaticContext;
import ip91.chui.oleh.algorithm.model.Result;
import java.util.Map;

public final class StatsUtils {

  private static final String EVENT_TYPE = "CustomEvent";

  private static final String SELECTION = "Selection";
  private static final String CROSSOVER = "Crossover";
  private static final String MUTATION = "Mutation";
  private static final String GENERATION_REPLACEMENT = "GenerationReplacement";

  private static final String OPERATION_TYPE_KEY = "operationType";
  private static final String GENERATIONS_COUNT_KEY = "generationsCount";
  private static final String MIN_TIME_KEY = "minTime";
  private static final String MAX_TIME_KEY = "maxTime";
  private static final String AVG_TIME_KEY = "avgTime";
  private static final String SUM_TIME_KEY = "sumTime";

  private static final String X = "";

  private static final int GENERATION_COUNT_TO_SEND_DATA = 500;

  private static long selectionSumTime;
  private static long selectionTotalTimeInSeconds;
  private static long selectionMaxTime = Long.MIN_VALUE;
  private static long selectionMinTime = Long.MAX_VALUE;
  private static int count;

  public static void collectSelectionTime(long time, int generationsCount) {
    if (generationsCount == 0) {
      return;
    }

    count++;
    selectionSumTime += time;
    selectionMaxTime = Math.max(time, selectionMaxTime);
    selectionMinTime = Math.min(time, selectionMinTime);

    if (generationsCount > 0 && generationsCount % GENERATION_COUNT_TO_SEND_DATA == 0) {
      Map<String, Object> eventAttributes = Map.of(
          OPERATION_TYPE_KEY, SELECTION,
          GENERATIONS_COUNT_KEY, generationsCount,
          MIN_TIME_KEY, selectionMinTime,
          MAX_TIME_KEY, selectionMaxTime,
          AVG_TIME_KEY, (selectionSumTime / count),
          SUM_TIME_KEY, selectionSumTime);
      NewRelic.getAgent().getInsights().recordCustomEvent(EVENT_TYPE, eventAttributes);

      count = 0;
      selectionSumTime = 0;
      selectionMaxTime = Long.MIN_VALUE;
      selectionMinTime = Long.MAX_VALUE;
    }
  }

  public static void collectFitnessFunctionStepsInfo(int generationCount) {
    if (generationCount == 0 || generationCount % 100 != 0) {
      return;
    }

    Map<String, Object> executedStepsAttributes = Map.of(
        "eventName", "fitnessFunctionStep",
        "tag", "виконано",
        "generation", generationCount,
        "count", StaticContext.getExecutedFitnessFunctionStepsCount());

    Map<String, Object> skippedStepsAttributes = Map.of(
        "eventName", "fitnessFunctionStep",
        "tag", "пропущено",
        "generation", generationCount,
        "count", StaticContext.getSkippedFitnessFunctionStepsCount());

    NewRelic.getAgent().getInsights().recordCustomEvent(EVENT_TYPE, executedStepsAttributes);
    NewRelic.getAgent().getInsights().recordCustomEvent(EVENT_TYPE, skippedStepsAttributes);

    StaticContext.resetFitnessFunctionStepsCount();
  }

  public static void collectTransientStats(Result result) {
    final int generation = result.getGeneration();
    final int fitnessScore = result.getBestIndividual().getFitness();

    Map<String, Object> eventAttributes = Map.of(
        "generationsCount", generation,
        "score", fitnessScore);
    NewRelic.getAgent().getInsights().recordCustomEvent("CustomEvent", eventAttributes);
  }
}
