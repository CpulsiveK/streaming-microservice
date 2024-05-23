package com.cpulsivek.uploadservice.service.userDetails;

import java.util.Collection;
import java.util.Collections;

import com.cpulsivek.uploadservice.client.UserClient;
import com.cpulsivek.uploadservice.dto.GetUserDto;
import com.cpulsivek.uploadservice.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserClient userClient;

  @Autowired
  public UserDetailsServiceImpl(UserClient userClient) {
    this.userClient = userClient;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    GetUserDto getUserDto = new GetUserDto();
    getUserDto.setUsername(username);

    User user = userClient.findByUsername(getUserDto);

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        getAuthorities(user));
  }

  private Collection<GrantedAuthority> getAuthorities(User user) {
    return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
  }
}
