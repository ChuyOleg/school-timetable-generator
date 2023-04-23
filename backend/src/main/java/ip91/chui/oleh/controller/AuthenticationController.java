package ip91.chui.oleh.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import ip91.chui.oleh.model.dto.auth.AuthenticationRequest;
import ip91.chui.oleh.model.dto.auth.RegisterRequest;
import ip91.chui.oleh.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static ip91.chui.oleh.config.security.JwtAuthenticationFilter.*;

@RestController
@RequestMapping("/v1/auth")
@SecurityRequirements
@RequiredArgsConstructor
public class AuthenticationController {

  private static final String ACCESS_CONTROL = "Access-Control-Expose-Headers";

  private final AuthenticationService service;

  @PostMapping("/register")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void register(@RequestBody RegisterRequest request, HttpServletResponse response) {
    String authToken = service.register(request);
    setAuthToken(authToken, response);
  }

  @PostMapping("/authenticate")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
    String authToken = service.authenticate(request);
    setAuthToken(authToken, response);
  }

  @GetMapping("/existsByEmail")
  public boolean existsByEmail(@RequestParam String email) {
    return service.existByEmail(email);
  }


  private void setAuthToken(String authToken, HttpServletResponse response) {
    response.setHeader(ACCESS_CONTROL, AUTH_HEADER_NAME);
    response.setHeader(AUTH_HEADER_NAME, JWT_PREFIX + authToken);
  }

}
