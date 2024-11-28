package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.mutation.Mutation;
import ip91.chui.oleh.algorithm.mutation.NaturalMutation;
import ip91.chui.oleh.algorithm.util.holder.TimeSlotsHolder;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MutationConfig {

  @Bean
  public Mutation naturalMutation(TimeSlotsHolder timeSlotsHolder, Random random) {
    return new NaturalMutation(timeSlotsHolder, random);
  }
}
