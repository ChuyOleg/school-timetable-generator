package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.algorithm.conditionData.KnapsackConditionData;
import ip91.chui.oleh.algorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.config.GenerationReplacementType;
import ip91.chui.oleh.algorithm.crossover.Crossover;
import ip91.chui.oleh.algorithm.crossover.FairPointCrossover;
import ip91.chui.oleh.algorithm.crossover.RandomPointCrossover;
import ip91.chui.oleh.algorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.algorithm.crossover.chromosomeController.KnapsackChromosomeController;
import ip91.chui.oleh.algorithm.crossover.chromosomeController.SalesmanChromosomeController;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.KnapsackFitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.SalesmanFitnessFunction;
import ip91.chui.oleh.algorithm.generationReplacement.AllOffspringIntoPopulationGenerationReplacement;
import ip91.chui.oleh.algorithm.generationReplacement.GenerationReplacement;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import ip91.chui.oleh.algorithm.model.Result;
import ip91.chui.oleh.algorithm.model.RuntimeInfo;
import ip91.chui.oleh.algorithm.mutation.Mutation;
import ip91.chui.oleh.algorithm.mutation.OppositeBooleanValueMutation;
import ip91.chui.oleh.algorithm.mutation.SwapGenesMutation;
import ip91.chui.oleh.algorithm.selection.HalfPopulationSelection;
import ip91.chui.oleh.algorithm.selection.OneBestOneRandomSelection;
import ip91.chui.oleh.algorithm.selection.Selection;
import ip91.chui.oleh.algorithm.selection.TwoBestSelection;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class EvolutionaryAlgorithm {

  private final KnapsackConditionData knapsackConditionData;
  private final SalesmanConditionData salesmanConditionData;

  private Selection selection;
  private Crossover crossover;
  private Mutation mutation;
  private GenerationReplacement generationReplacement;

  public void init() {
    FitnessFunction fitnessFunction = getFitnessFunction();
    ChromosomeController chromosomeController = getChromosomeController();
    Random random = new Random();

    initSelection(random);
    initCrossover(chromosomeController, random);
    initMutation(fitnessFunction, random);
    initGenerationReplacement();
  }

  public Result run(Population population, RuntimeInfo info) {
    sortPopulationBasedOnTaskType(population);
    int generationCounter = 0;

    while (generationCounter < Config.MAX_GENERATION_NUMBER && info.getBestIndividualNotChangeCounter() < Config.GENERATION_WITHOUT_CHANGING_LIMIT) {
      List<Individual> bestParents = selection.process(population);

      List<Individual> offspring = crossover.process(bestParents);

      mutation.process(offspring);

      generationReplacement.process(population, offspring);

      sortPopulationBasedOnTaskType(population);

      changeBestIndividualBasedOnTaskTypeIfPossible(population, info);

      generationCounter++;
    }

    return new Result(info.getBestIndividual(), generationCounter);
  }

  private FitnessFunction getFitnessFunction() {
    return switch (Config.TASK_NAME) {
      case KNAPSACK -> new KnapsackFitnessFunction(knapsackConditionData);
      case SALESMAN -> new SalesmanFitnessFunction(salesmanConditionData);
    };
  }

  private ChromosomeController getChromosomeController() {
    return switch (Config.TASK_NAME) {
      case KNAPSACK -> new KnapsackChromosomeController();
      case SALESMAN -> new SalesmanChromosomeController();
    };
  }

  private void initSelection(Random random) {
    switch (Config.SELECTION_TYPE) {
      case HALF_POPULATION -> selection = new HalfPopulationSelection();
      case ONE_BEST_ONE_RANDOM -> selection = new OneBestOneRandomSelection(random);
      case TWO_BEST -> selection = new TwoBestSelection();
    }
  }

  private void initCrossover(ChromosomeController chromosomeController, Random random) {
    switch (Config.CROSSOVER_TYPE) {
      case FAIR_POINT -> crossover = new FairPointCrossover(chromosomeController);
      case RANDOM_POINT -> crossover = new RandomPointCrossover(chromosomeController, random);
    }
  }

  private void initMutation(FitnessFunction fitnessFunction, Random random) {
    switch (Config.MUTATION_TYPE) {
      case SWAP_GENES -> mutation = new SwapGenesMutation(fitnessFunction, random);
      case OPPOSITE_VALUE -> mutation = new OppositeBooleanValueMutation(fitnessFunction, random);
    }
  }

  private void initGenerationReplacement() {
    if (Config.GENERATION_REPLACEMENT_TYPE == GenerationReplacementType.All_OFFSPRING_INTO_POPULATION) {
      generationReplacement = new AllOffspringIntoPopulationGenerationReplacement();
    }
  }

  private void sortPopulationBasedOnTaskType(Population population) {
    switch (Config.TASK_TYPE) {
      case MAXIMIZATION -> population.individuals().sort(Comparator.comparingInt(Individual::getFitness));
      case MINIMIZATION -> population.individuals().sort((i1, i2) -> i2.getFitness() - i1.getFitness());
    }
  }

  private void changeBestIndividualBasedOnTaskTypeIfPossible(Population population, RuntimeInfo info) {
    Individual bestInPopulation = population.individuals().get(population.individuals().size() - 1);

    switch (Config.TASK_TYPE) {
      case MAXIMIZATION ->
          changeBestIndividualIfPossible(info, bestInPopulation, (ind) -> ind.getFitness() > info.getBestIndividual().getFitness());
      case MINIMIZATION ->
          changeBestIndividualIfPossible(info, bestInPopulation, (ind) -> ind.getFitness() < info.getBestIndividual().getFitness());
    }
  }

  private void changeBestIndividualIfPossible(RuntimeInfo info, Individual individual, Predicate<Individual> predicate) {
    if (info.getBestIndividual() == null || predicate.test(individual)) {
      info.setBestIndividual(individual);
      info.setBestIndividualNotChangeCounter(0);
    } else {
      info.setBestIndividualNotChangeCounter(info.getBestIndividualNotChangeCounter() + 1);
    }
  }

}
