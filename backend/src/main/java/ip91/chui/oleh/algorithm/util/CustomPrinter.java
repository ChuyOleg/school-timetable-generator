package ip91.chui.oleh.algorithm.util;

import ip91.chui.oleh.algorithm.conditionData.KnapsackConditionData;
import ip91.chui.oleh.algorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.algorithm.config.Config;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class CustomPrinter {

  private CustomPrinter() {}

  private final static String WEIGHT_TABLE = "Weight table";
  private final static String PRICE_TABLE = "Price table";
  private final static String MAX_WEIGHT = "Max Weight";

  private final static String ROAD_MATRIX = "Road matrix";

  private final static String ARRAY_SPLIT_REGEX = ", ";
  private final static String SUB_ARRAY_SPLIT_REGEX = "], ";

  public static void writeBackpackConditionDataToFile(KnapsackConditionData data) throws IOException {
    FileWriter fileWriter = new FileWriter(Config.KNAPSACK_CONDITION_DATA_FILE_NAME);
    PrintWriter printWriter = new PrintWriter(fileWriter);

    printWriter.println(WEIGHT_TABLE);
    printWriter.println(Arrays.toString(data.weightTable()));

    printWriter.println();

    printWriter.println(PRICE_TABLE);
    printWriter.println(Arrays.toString(data.priceTable()));

    printWriter.println();
    printWriter.println(MAX_WEIGHT);
    printWriter.println(data.maxWeight());

    printWriter.close();
  }

  public static void writeSalesmanConditionDataToFile(SalesmanConditionData data) throws IOException {
    FileWriter fileWriter = new FileWriter(Config.SALESMAN_CONDITION_DATA_FILE_NAME);
    PrintWriter printWriter = new PrintWriter(fileWriter);

    printWriter.println(ROAD_MATRIX);
    printWriter.println(Arrays.deepToString(data.roadMatrix()));

    printWriter.close();
  }

  public static KnapsackConditionData readBackpackConditionDataFromFile() {
    int[] weightTable = null;
    int[] priceTable = null;
    int maxWeight = 0;

    Scanner scanner;
    try {
      scanner = new Scanner(new File(Config.KNAPSACK_CONDITION_DATA_FILE_NAME));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      switch (line) {
        case WEIGHT_TABLE: {
          String nextLine = scanner.nextLine();
          String[] stringArray = nextLine.substring(1, nextLine.length() - 1).split(ARRAY_SPLIT_REGEX);
          weightTable = convertStringArrayIntoIntArray(stringArray);
          break;
        }
        case PRICE_TABLE: {
          String nextLine = scanner.nextLine();
          String[] stringArray = nextLine.substring(1, nextLine.length() - 1).split(ARRAY_SPLIT_REGEX);
          priceTable = convertStringArrayIntoIntArray(stringArray);
          break;
        }
        case MAX_WEIGHT: {
          String nextLine = scanner.nextLine();
          maxWeight = Integer.parseInt(nextLine);
          break;
        }
      }
    }

    return new KnapsackConditionData(weightTable, priceTable, maxWeight);
  }

  public static SalesmanConditionData readSalesmanConditionDataFromFile() {
    int[][] roadMatrix = null;

    Scanner scanner;
    try {
      scanner = new Scanner(new File(Config.SALESMAN_CONDITION_DATA_FILE_NAME));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      if (line.equals(ROAD_MATRIX)) {
        String nextLine = scanner.nextLine();
        String[] stringArrayOfArray = nextLine.substring(1, nextLine.length() - 2).split(SUB_ARRAY_SPLIT_REGEX);

        int[][] zx = new int[stringArrayOfArray.length][];

        for (int index = 0; index < stringArrayOfArray.length; index++) {
          String string = stringArrayOfArray[index];

          String[] stringArray = string.substring(1).split(ARRAY_SPLIT_REGEX);

          zx[index] = convertStringArrayIntoIntArray(stringArray);
        }

        roadMatrix = zx;
      }
    }

    return new SalesmanConditionData(roadMatrix);
  }

  private static int[] convertStringArrayIntoIntArray(String[] stringArray) {
    int[] result = new int[stringArray.length];

    for (int index = 0; index < stringArray.length; index++) {
      result[index] = Integer.parseInt(stringArray[index]);
    }

    return result;
  }

}
