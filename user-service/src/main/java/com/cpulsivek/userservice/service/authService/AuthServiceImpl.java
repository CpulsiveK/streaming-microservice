package com.cpulsivek.userservice.service.authService;

import com.cpulsivek.userservice.dto.LoginRequestDto;
import com.cpulsivek.userservice.dto.LoginResponseDto;
import com.cpulsivek.userservice.dto.SignupRequestDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.exception.DuplicateException;
import com.cpulsivek.userservice.repository.UserRepository;
import com.cpulsivek.userservice.service.jwtService.JwtService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Autowired
  public AuthServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @Override
  public User signup(SignupRequestDto signupRequestDto) {
    String username = signupRequestDto.username();
    Optional<User> optionalUser = userRepository.findByUsername(username);

    if (optionalUser.isPresent()) throw new DuplicateException(username + " already exists");

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(signupRequestDto.password()));

    return userRepository.save(user);
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    String username = loginRequestDto.username();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, loginRequestDto.password()));

    return new LoginResponseDto(jwtService.generateToken(username));
  }
}
