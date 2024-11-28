package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.generationReplacement.AllOffspringIntoPopulationGenerationReplacement;
import ip91.chui.oleh.algorithm.generationReplacement.BestOffspringIntoPopulationGenerationReplacement;
import ip91.chui.oleh.algorithm.generationReplacement.GenerationReplacement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerationReplacementConfig {

  @Bean
  public GenerationReplacement allAliveOffspringIntoPopulationGenerationReplacement() {
    return new AllOffspringIntoPopulationGenerationReplacement();
  }

  @Bean
  public GenerationReplacement bestOffspringIntoPopulationGenerationReplacement() {
    return new BestOffspringIntoPopulationGenerationReplacement();
  }
}
