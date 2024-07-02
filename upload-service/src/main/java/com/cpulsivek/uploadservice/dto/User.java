package com.cpulsivek.uploadservice.dto;

import java.util.List;

public record User(
    Long id,
    String username,
    String password,
    boolean enabled,
    boolean accountNonExpired,
    boolean credentialsNonExpired,
    List<String> authorities,
    boolean accountNonLocked) {}
