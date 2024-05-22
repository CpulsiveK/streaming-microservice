package com.cpulsivek.userservice.service.auth;

import com.cpulsivek.userservice.dto.LoginRequestDto;
import com.cpulsivek.userservice.dto.LoginResponseDto;
import com.cpulsivek.userservice.dto.SignupRequestDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.exception.DuplicateException;
import com.cpulsivek.userservice.repository.UserRepository;
import java.util.Optional;

import com.cpulsivek.userservice.service.jwt.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImpl implements Auth {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final Jwt jwt;

  @Autowired
  public AuthImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      Jwt jwt) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwt = jwt;
  }

  @Override
  public User signup(SignupRequestDto signupRequestDto) {
    String username = signupRequestDto.getUsername();
    Optional<User> optionalUser = userRepository.findByUsername(username);

    if (optionalUser.isPresent()) throw new DuplicateException(username + "already exists");

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
    user.setRole("USER");

    return userRepository.save(user);
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    String username = loginRequestDto.getUsername();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, loginRequestDto.getPassword()));

    LoginResponseDto loginResponseDto = new LoginResponseDto();
    loginResponseDto.setToken(jwt.generateToken(username));

    return loginResponseDto;
  }
}
