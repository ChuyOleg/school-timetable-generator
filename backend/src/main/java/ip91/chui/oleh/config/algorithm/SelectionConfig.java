package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.selection.HalfPopulationSelection;
import ip91.chui.oleh.algorithm.selection.OneBestOneRandomSelection;
import ip91.chui.oleh.algorithm.selection.Selection;
import ip91.chui.oleh.algorithm.selection.TwoBestSelection;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelectionConfig {

  @Bean
  public Selection halfPopulationSelection() {
    return new HalfPopulationSelection();
  }

  @Bean
  public Selection oneBestOneRandomSelection(Random random) {
    return new OneBestOneRandomSelection(random);
  }

  @Bean
  public Selection twoBestSelection() {
    return new TwoBestSelection();
  }
}
