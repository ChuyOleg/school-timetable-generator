package ip91.chui.oleh.algorithm;

import ip91.chui.oleh.algorithm.crossover.Crossover;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.generationReplacement.GenerationReplacement;
import ip91.chui.oleh.algorithm.model.Individual;
import ip91.chui.oleh.algorithm.model.Population;
import ip91.chui.oleh.algorithm.model.Result;
import ip91.chui.oleh.algorithm.model.RuntimeInfo;
import ip91.chui.oleh.algorithm.mutation.Mutation;
import ip91.chui.oleh.algorithm.populationGenerator.PopulationGenerator;
import ip91.chui.oleh.algorithm.selection.Selection;
import ip91.chui.oleh.algorithm.util.IndividualMapper;
import ip91.chui.oleh.algorithm.util.LoggerUtils;
import ip91.chui.oleh.config.properties.algorithm.AlgorithmConfigProperties;
import ip91.chui.oleh.model.dto.TimeTableDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class EvolutionaryAlgorithm implements Algorithm {

  private final AlgorithmConfigProperties config;

  private final PopulationGenerator populationGenerator;
  private final Selection selection;
  private final Crossover crossover;
  private final Mutation mutation;
  private final FitnessFunction fitnessFunction;
  private final GenerationReplacement generationReplacement;

  public TimeTableDto generate() {
    Result result = generateInternal();

    LoggerUtils.logUltimateResult(result);

    return IndividualMapper.toTimetableDto(result.getBestIndividual());
  }

  private Result generateInternal() {
    Population population = populationGenerator.generate(config.getPopulationSize());
    sortPopulation(population);
    RuntimeInfo runtimeInfo = new RuntimeInfo();
    changeBestIndividualIfPossible(population, runtimeInfo);

    int generationCounter = 0;
    long executionStart = System.nanoTime();

    while (canAlgorithmContinue(generationCounter, runtimeInfo)) {
      long selectionStart = System.nanoTime();
      List<Individual> bestParents = selection.process(population);
//      StatsUtils.collectSelectionTime((System.nanoTime() - selectionStart), generationCounter);

      List<Individual> offspring = crossover.process(bestParents);
      mutation.process(offspring, config.getMutationMeasure(), config.getMutationPercentage());
      fitnessFunction.calculate(offspring, runtimeInfo);

      generationReplacement.process(population, offspring);

      sortPopulation(population);
      changeBestIndividualIfPossible(population, runtimeInfo);


//      StatsUtils.collectFitnessFunctionStepsInfo(generationCounter);
      LoggerUtils.logTransientResult(generationCounter, runtimeInfo);

      generationCounter++;

      if (generationCounter > 0 && generationCounter % 250 == 0) {
        log.info("Transient. Generation: {}. Time: {}", generationCounter, ((System.nanoTime() - executionStart) / 1_000_000_000));
      }
    }

    log.info("Done. Generation: {}. Time: {}", generationCounter, ((System.nanoTime() - executionStart) / 1_000_000_000));

    return new Result(runtimeInfo.getBestIndividual(), generationCounter);
  }

  private void sortPopulation(Population population) {
    population.individuals().sort(Comparator.comparingInt(Individual::getFitness).reversed());
  }

  private boolean canAlgorithmContinue(int generationCounter, RuntimeInfo info) {
    return generationCounter < config.getMaxGenerationNumber()
        && info.getBestIndividualNotChangeCounter() < config.getGenerationWithoutChangingLimit()
        && !isIndividualPerfect(info);
  }

  private boolean isIndividualPerfect(RuntimeInfo info) {
    return info.getBestIndividual().getFitness() == 0;
  }

  private void changeBestIndividualIfPossible(Population population, RuntimeInfo runtimeInfo) {
    Individual bestIndividual = population.individuals().get(population.individuals().size() - 1);

    if (Objects.nonNull(runtimeInfo.getBestIndividual()) && runtimeInfo.getBestIndividual().getFitness() <= bestIndividual.getFitness()) {
      runtimeInfo.incrementBestIndividualNotChangeCounter();
      return;
    }

    runtimeInfo.setBestIndividual(bestIndividual);
    runtimeInfo.resetBestIndividualNotChangeCounter();
  }
}
