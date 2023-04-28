package ip91.chui.oleh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class AppConfig {

  @Bean
  public Random random() {
    return new Random();
  }

}
