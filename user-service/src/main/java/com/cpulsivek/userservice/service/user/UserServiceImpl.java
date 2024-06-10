package com.cpulsivek.userservice.service.user;

import com.cpulsivek.userservice.dto.GetUserDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUser(GetUserDto getUserDto) {
    return userRepository
        .findByUsername(getUserDto.username())
        .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
  }
}
