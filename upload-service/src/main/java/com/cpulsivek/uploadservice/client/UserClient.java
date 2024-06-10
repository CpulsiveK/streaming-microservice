package com.cpulsivek.uploadservice.client;

import com.cpulsivek.uploadservice.dto.GetUserDto;
import com.cpulsivek.uploadservice.dto.User;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/v1/user")
public interface UserClient {
  @GetExchange("/profile")
  User findByUsername(@RequestHeader Map<String, String> headers, @RequestBody GetUserDto getUserDto);

    User findByUsername(String token);
}
