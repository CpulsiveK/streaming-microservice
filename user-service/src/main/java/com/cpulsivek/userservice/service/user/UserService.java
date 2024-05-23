package com.cpulsivek.userservice.service.user;

import com.cpulsivek.userservice.dto.GetUserDto;
import com.cpulsivek.userservice.entity.User;

public interface UserService {
    User getUser(GetUserDto getUserDto);
}
