package com.cpulsivek.uploadservice.client;

import com.cpulsivek.uploadservice.dto.GetUserDto;
import com.cpulsivek.uploadservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8090/api/v1/auth")
public interface UserClient {
    @GetMapping("/user")
    User findByUsername(@RequestBody GetUserDto getUserDto);
}
