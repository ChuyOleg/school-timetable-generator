package ip91.chui.oleh.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi30Config {

  private static final String SECURITY_SCHEME_NAME = "bearerAuth";
  private static final String BEARER_SCHEME = "bearer";
  private static final String JWT_BEARER_FORMAT = "JWT";

  @Bean
  public OpenAPI customizeOpenApi() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement()
            .addList(SECURITY_SCHEME_NAME))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER_SCHEME)
                .bearerFormat(JWT_BEARER_FORMAT)
            )
        );
  }

}
