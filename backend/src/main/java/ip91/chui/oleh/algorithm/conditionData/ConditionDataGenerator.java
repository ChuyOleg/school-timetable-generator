package ip91.chui.oleh.algorithm.conditionData;

import ip91.chui.oleh.algorithm.config.Config;

import java.util.Random;

/**
 The class provides methods to generate different types of condition data for different problem domains.
 The class is defined as a utility class and has a private constructor to prevent creating instances of this class.
 @author Chui Oleh
 */
public class ConditionDataGenerator {

  private ConditionDataGenerator() {}

  /**
   Generates random condition data for the knapsack problem.
   The generated data includes the weight and price of each item, and the maximum weight that the knapsack can carry.
   The random values are generated based on the values in the {@link Config} class.
   @return a {@link KnapsackConditionData} instance with the generated data
   */
  public static KnapsackConditionData knapsack() {
    int[] weightTable = new int[Config.KNAPSACK_THING_COUNT];
    int[] priceTable = new int[Config.KNAPSACK_THING_COUNT];
    Random random = new Random();

    for (int thingNumber = 0; thingNumber < Config.KNAPSACK_THING_COUNT; thingNumber++) {
      int randomWeight = random.nextInt(Config.THING_MAX_WEIGHT - Config.THING_MIN_WEIGHT + 1) + Config.THING_MIN_WEIGHT;
      int randomPrice = random.nextInt(Config.THING_MAX_PRICE - Config.THING_MIN_PRICE + 1) + Config.THING_MIN_PRICE;

      weightTable[thingNumber] = randomWeight;
      priceTable[thingNumber] = randomPrice;
    }

    return new KnapsackConditionData(weightTable, priceTable, Config.KNAPSACK_MAX_WEIGHT);
  }

  /**
   Generates random condition data for the traveling salesman problem.
   The generated data includes the distances between each city.
   The random values are generated based on the values in the {@link Config} class.
   @return a {@link SalesmanConditionData} instance with the generated data
   */
  public static SalesmanConditionData salesman() {
    int[][] roadMatrix = new int[Config.SALESMAN_CITY_COUNT][Config.SALESMAN_CITY_COUNT];
    Random random = new Random();

    for (int source = 0; source < Config.SALESMAN_CITY_COUNT; source++) {
      for (int destination = 0; destination < Config.SALESMAN_CITY_COUNT; destination++) {
        if (source == destination) {
          roadMatrix[source][destination] = Integer.MAX_VALUE;
        } else if (source < destination) {
          int randomLength = random.nextInt(Config.SALESMAN_MAX_LENGTH - Config.SALESMAN_MIN_LENGTH + 1) + Config.SALESMAN_MIN_LENGTH;
          roadMatrix[source][destination] = randomLength;
        } else {
          roadMatrix[source][destination] = roadMatrix[destination][source];
        }
      }
    }

    return new SalesmanConditionData(roadMatrix);
  }

}
