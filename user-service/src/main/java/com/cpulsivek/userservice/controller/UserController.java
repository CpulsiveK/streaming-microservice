package com.cpulsivek.userservice.controller;

import com.cpulsivek.userservice.dto.GetUserDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user")
  public ResponseEntity<User> getUser(@RequestBody GetUserDto getUserDto) {
    return new ResponseEntity<>(userService.getUser(getUserDto), HttpStatus.OK);
  }
}
