package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.Algorithm;
import ip91.chui.oleh.algorithm.EvolutionaryAlgorithm;
import ip91.chui.oleh.algorithm.crossover.Crossover;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.generationReplacement.GenerationReplacement;
import ip91.chui.oleh.algorithm.mutation.Mutation;
import ip91.chui.oleh.algorithm.populationGenerator.PopulationGenerator;
import ip91.chui.oleh.algorithm.selection.Selection;
import ip91.chui.oleh.config.properties.algorithm.AlgorithmComponentsProperties;
import ip91.chui.oleh.config.properties.algorithm.AlgorithmConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    AlgorithmComponentsProperties.class, AlgorithmConfigProperties.class })
public class AlgorithmConfig {

  @Bean
  public Algorithm evolutionaryAlgorithm(
      AlgorithmConfigProperties config, AlgorithmComponentsProperties props,
      FitnessFunction fitnessFunction, ApplicationContext context) {

    PopulationGenerator populationGenerator =
        (PopulationGenerator) context.getBean(props.getPopulationGeneratorName());
    Selection selection = (Selection) context.getBean(props.getSelectionName());
    Crossover crossover = (Crossover) context.getBean(props.getCrossoverName());
    Mutation mutation = (Mutation) context.getBean(props.getMutationName());
    GenerationReplacement generationReplacement =
        (GenerationReplacement) context.getBean(props.getGenerationReplacementName());

    return new EvolutionaryAlgorithm(config, populationGenerator,
        selection, crossover, mutation, fitnessFunction, generationReplacement);
  }
}
