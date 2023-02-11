package ip91.chui.oleh.algorithm.config;

public class Config {

  private Config() {}

  // BACKPACK vs SALESMAN
  public static final TaskName TASK_NAME = TaskName.SALESMAN;
  // MAXIMIZATION vs MINIMIZATION
  public static final TaskType TASK_TYPE = TaskType.MINIMIZATION;
  public static final int TEST_PERFORMANCE_ITERATION_NUM = 5;

  // RANDOM vs FROM_FILE
  public static final ConditionDataType CONDITION_DATA_TYPE = ConditionDataType.RANDOM;
  public static final String KNAPSACK_CONDITION_DATA_FILE_NAME = "src/main/resources/backpackConditionData_1.txt";
  public static final String SALESMAN_CONDITION_DATA_FILE_NAME = "src/main/resources/salesmanConditionData_1.txt";

  // TWO_BEST vs ONE_BEST_ONE_RANDOM vs HALF_POPULATION
  public static final SelectionType SELECTION_TYPE = SelectionType.HALF_POPULATION;
  // FAIR_POINT vs RANDOM_POINT
  public static final CrossoverType CROSSOVER_TYPE = CrossoverType.RANDOM_POINT;
  // BACKPACK -> OPPOSITE_VALUE vs SWAP_GENES; SALESMAN -> only SWAP_GENES
  public static final MutationType MUTATION_TYPE = MutationType.SWAP_GENES;
  // All_OFFSPRING_INTO_POPULATION
  public static final GenerationReplacementType GENERATION_REPLACEMENT_TYPE = GenerationReplacementType.All_OFFSPRING_INTO_POPULATION;

  public static final int MAX_GENERATION_NUMBER = 100;
  public static final int GENERATION_WITHOUT_CHANGING_LIMIT = 50;

  public static final int POPULATION_SIZE = 10;
  // IF MUTATION_MEASURE = 100, MUTATION_PERCENTAGE = 1 then chance for mutation = 1/100;
  public static final int MUTATION_MEASURE = 100;
  public static final int MUTATION_PERCENTAGE = 1;

  /* Backpack Configuration */
  public static final int KNAPSACK_THING_COUNT = 5;
  public static final int THING_MIN_WEIGHT = 1;
  public static final int THING_MAX_WEIGHT = 20;
  public static final int KNAPSACK_MAX_WEIGHT = KNAPSACK_THING_COUNT * (THING_MAX_WEIGHT + THING_MIN_WEIGHT) / 3;
  public static final int THING_MIN_PRICE = 1;
  public static final int THING_MAX_PRICE = 15;
  //*****************************

  /* Salesman Configuration */
  public static final int SALESMAN_CITY_COUNT = 5;
  public static final int SALESMAN_MIN_LENGTH = 2;
  public static final int SALESMAN_MAX_LENGTH = 15;
  //*****************************

}
