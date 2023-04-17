package ip91.chui.oleh.service.auth;

import ip91.chui.oleh.config.security.JwtService;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.dto.auth.AuthenticationRequest;
import ip91.chui.oleh.model.dto.auth.RegisterRequest;
import ip91.chui.oleh.model.enumeration.Role;
import ip91.chui.oleh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private static final String USER_NOT_FOUND_ERR_MSG = "User (%s) not found";

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public String register(RegisterRequest request) {
    User user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();

    repository.save(user);
    return jwtService.generateToken(user);
  }

  public String authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    User user = repository.findByEmail(request.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_ERR_MSG, request.getEmail())));
    return jwtService.generateToken(user);
  }

  public boolean existByEmail(String email) {
    return repository.existsByEmail(email);
  }

}
