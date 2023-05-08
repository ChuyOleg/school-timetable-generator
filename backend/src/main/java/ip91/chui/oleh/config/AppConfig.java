package ip91.chui.oleh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Random;

@Configuration
@EnableJpaAuditing
public class AppConfig {

  @Bean
  public Random random() {
    return new Random();
  }

}
