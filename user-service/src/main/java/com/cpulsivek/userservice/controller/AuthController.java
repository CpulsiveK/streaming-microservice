package com.cpulsivek.userservice.controller;

import com.cpulsivek.userservice.dto.LoginRequestDto;
import com.cpulsivek.userservice.dto.LoginResponseDto;
import com.cpulsivek.userservice.dto.SignupRequestDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.service.auth.Auth;
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
  private final Auth auth;

  @Autowired
  public AuthController(Auth auth) {
    this.auth = auth;
  }

  @PostMapping("/signup")
  public ResponseEntity<User> signup(@RequestBody SignupRequestDto signupRequestDto) {
    return new ResponseEntity<>(auth.signup(signupRequestDto), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    return new ResponseEntity<>(auth.login(loginRequestDto), HttpStatus.OK);
  }
}
