package com.cpulsivek.userservice.controller;

import com.cpulsivek.userservice.dto.LoginRequestDto;
import com.cpulsivek.userservice.dto.LoginResponseDto;
import com.cpulsivek.userservice.dto.SignupRequestDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<User> signup(@RequestBody SignupRequestDto signupRequestDto) {
    return new ResponseEntity<>(authService.signup(signupRequestDto), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
  }
}
