package com.cpulsivek.userservice.service.user;

import com.cpulsivek.userservice.dto.GetUserDto;
import com.cpulsivek.userservice.entity.User;
import com.cpulsivek.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUser(GetUserDto getUserDto) {
    Optional<User> optionalUser = userRepository.findByUsername(getUserDto.getUsername());

    if (optionalUser.isEmpty()) throw new UsernameNotFoundException("");

    return optionalUser.get();
  }
}
