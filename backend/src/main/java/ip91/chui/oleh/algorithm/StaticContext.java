package ip91.chui.oleh.algorithm;

public class StaticContext {

  private static long executedFitnessFunctionStepsCount;
  private static long skippedFitnessFunctionStepsCount;

  public static void incExecutedFitnessFunctionStepsCount() {
    executedFitnessFunctionStepsCount++;
  }

  public static void incSkippedFitnessFunctionStepsCount() {
    skippedFitnessFunctionStepsCount++;
  }

  public static void resetFitnessFunctionStepsCount() {
    executedFitnessFunctionStepsCount = 0;
    skippedFitnessFunctionStepsCount = 0;
  }

  public static long getExecutedFitnessFunctionStepsCount() {
    return executedFitnessFunctionStepsCount;
  }

  public static long getSkippedFitnessFunctionStepsCount() {
    return skippedFitnessFunctionStepsCount;
  }
}
