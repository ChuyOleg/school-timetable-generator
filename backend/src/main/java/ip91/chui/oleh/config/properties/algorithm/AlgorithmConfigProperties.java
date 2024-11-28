package ip91.chui.oleh.config.properties.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "algorithm.config")
public class AlgorithmConfigProperties {

  private int maxGenerationNumber;
  private int generationWithoutChangingLimit;
  private int populationSize;
  private int mutationMeasure;
  private int mutationPercentage;
}
