package ip91.chui.oleh.config.properties.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "algorithm.components")
public class AlgorithmComponentsProperties {

    private String populationGeneratorName;
    private String selectionName;
    private String crossoverName;
    private String mutationName;
    private String generationReplacementName;
}
