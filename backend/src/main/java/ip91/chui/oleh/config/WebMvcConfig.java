package ip91.chui.oleh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${web.cors.path-mapping}")
  private String corsPathMapping;

  @Value("${web.cors.allowed-origins}")
  private String corsAllowedOrigins;

  @Value("${web.cors.allowed-methods}")
  private String corsAllowedMethods;

  @Value("${web.cors.max-age}")
  private long corsMaxAge;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping(corsPathMapping)
        .allowedOrigins(corsAllowedOrigins)
        .allowedMethods(corsAllowedMethods.split(","))
        .maxAge(corsMaxAge);
  }
}
