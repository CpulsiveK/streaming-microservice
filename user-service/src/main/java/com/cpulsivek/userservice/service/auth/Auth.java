package com.cpulsivek.userservice.service.auth;

import com.cpulsivek.userservice.dto.LoginRequestDto;
import com.cpulsivek.userservice.dto.LoginResponseDto;
import com.cpulsivek.userservice.dto.SignupRequestDto;
import com.cpulsivek.userservice.entity.User;

public interface Auth {
    User signup(SignupRequestDto signupRequestDto);

    LoginResponseDto login(LoginRequestDto signupRequestDto);
}
