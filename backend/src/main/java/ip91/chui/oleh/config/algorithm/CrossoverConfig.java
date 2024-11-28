package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.crossover.Crossover;
import ip91.chui.oleh.algorithm.crossover.FairPointCrossover;
import ip91.chui.oleh.algorithm.crossover.OneByOneCrossover;
import ip91.chui.oleh.algorithm.crossover.RandomPointCrossover;
import ip91.chui.oleh.algorithm.crossover.chromosomeController.ChromosomeController;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrossoverConfig {

  @Bean
  public Crossover oneByOneCrossover() {
    return new OneByOneCrossover();
  }

  @Bean
  public Crossover fairPointCrossover(ChromosomeController chromosomeController) {
    return new FairPointCrossover(chromosomeController);
   }

   @Bean
   public Crossover randomPointCrossover(ChromosomeController chromosomeController, Random random) {
    return new RandomPointCrossover(chromosomeController, random);
   }
}
