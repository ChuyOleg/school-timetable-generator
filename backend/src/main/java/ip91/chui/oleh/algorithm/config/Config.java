package ip91.chui.oleh.algorithm.config;

public class Config {

  private Config() {}

  public static final TaskName TASK_NAME = TaskName.TIMETABLE;
  // MAXIMIZATION vs MINIMIZATION
  public static final TaskType TASK_TYPE = TaskType.MINIMIZATION;
  public static final int TEST_PERFORMANCE_ITERATION_NUM = 0;

  public static final int MAX_GENERATION_NUMBER = 10000;
  public static final int GENERATION_WITHOUT_CHANGING_LIMIT = 1250;

  public static final int POPULATION_SIZE = 20;
  // IF MUTATION_MEASURE = 100, MUTATION_PERCENTAGE = 1 then chance for mutation = 1/100;
  public static final int MUTATION_MEASURE = 100;
  public static final int MUTATION_PERCENTAGE = 10;

}
