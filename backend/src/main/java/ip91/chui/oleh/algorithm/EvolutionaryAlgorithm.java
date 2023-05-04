package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.crossover.Crossover;
import ip91.chui.oleh.algorithm.generationReplacement.GenerationReplacement;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import ip91.chui.oleh.algorithm.model.Result;
import ip91.chui.oleh.algorithm.model.RuntimeInfo;
import ip91.chui.oleh.algorithm.mutation.Mutation;
import ip91.chui.oleh.algorithm.populationGenerator.PopulationGenerator;
import ip91.chui.oleh.algorithm.selection.Selection;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.model.dto.TimeTableDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class EvolutionaryAlgorithm {

  private static final int ONE_MILLISECOND_IN_NANOSECONDS = 1000000;

  private final PopulationGenerator populationGenerator;
  private final Selection selection;
  private final Crossover crossover;
  private final Mutation mutation;
  private final GenerationReplacement generationReplacement;

  public EvolutionaryAlgorithm(
      PopulationGenerator populationGenerator,
      @Qualifier("twoBestSelection") Selection selection,
      @Qualifier("randomPointCrossover") Crossover crossover,
      Mutation mutation, GenerationReplacement generationReplacement
  ) {

    this.populationGenerator = populationGenerator;
    this.selection = selection;
    this.crossover = crossover;
    this.mutation = mutation;
    this.generationReplacement = generationReplacement;
  }

  public TimeTableDto generate() {
    if (Config.TEST_PERFORMANCE_ITERATION_NUM > 0) {
      testPerformance();
    }

    Population population = populationGenerator.generate();
    RuntimeInfo info = new RuntimeInfo(null, 0);
    Result result = run(population, info);

    Set<LessonDto> lessons = Arrays.stream(result.getBestIndividual().getChromosome())
        .map(gene -> (GroupDto) gene)
        .flatMap(group -> group.getLessons().stream())
        .collect(Collectors.toSet());

    System.out.println("---------------------------------------");
    System.out.println("Generation: " + result.getGeneration());
    System.out.println("Score: " + result.getBestIndividual().getFitness());

    return new TimeTableDto(null, lessons);
  }

  private Result run(Population population, RuntimeInfo info) {
    sortPopulationBasedOnTaskType(population);
    int generationCounter = 0;

    while (canAlgorithmContinue(generationCounter, info)) {
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

  private void sortPopulationBasedOnTaskType(Population population) {
    switch (Config.TASK_TYPE) {
      case MAXIMIZATION -> population.individuals().sort(Comparator.comparingInt(Individual::getFitness));
      case MINIMIZATION -> population.individuals().sort((i1, i2) -> i2.getFitness() - i1.getFitness());
    }
  }

  private boolean canAlgorithmContinue(int generationCounter, RuntimeInfo info) {
    return info.getBestIndividual() == null ||
        (generationCounter < Config.MAX_GENERATION_NUMBER &&
        info.getBestIndividualNotChangeCounter() < Config.GENERATION_WITHOUT_CHANGING_LIMIT &&
        !isIndividualPerfect(info)
        );
  }

  private boolean isIndividualPerfect(RuntimeInfo info) {
    return switch (Config.TASK_NAME) {
      case TIMETABLE -> info.getBestIndividual().getFitness() == 0;
    };
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

  private void testPerformance() {
    warmUpMachine();

    int resultFailCount = 0;
    long midDurationTime = 0;
    int midFitness = 0;

    for (int iter = 0; iter < Config.TEST_PERFORMANCE_ITERATION_NUM; iter++) {
      long startTime = System.nanoTime();
      Population population = populationGenerator.generate();
      RuntimeInfo info = new RuntimeInfo(null, 0);
      Result result = run(population, info);
      long endTime = System.nanoTime();

      long duration = (endTime - startTime);
      midDurationTime += duration;
      midFitness += result.getBestIndividual().getFitness();

      if (result.getBestIndividual().getFitness() != 0) {
        resultFailCount++;
      }
      System.out.println("Iter: " + iter +  " | Score: " + result.getBestIndividual().getFitness());
    }

    midDurationTime = (midDurationTime / Config.TEST_PERFORMANCE_ITERATION_NUM) / ONE_MILLISECOND_IN_NANOSECONDS;
    midFitness = midFitness / Config.TEST_PERFORMANCE_ITERATION_NUM;

    System.out.println("Duration: " + midDurationTime + " | midFitness: " + midFitness + " | resultFail: " + resultFailCount);
  }

  private void warmUpMachine() {
    Population population = populationGenerator.generate();
    RuntimeInfo info = new RuntimeInfo(null, 0);
    run(population, info);
  }

}
