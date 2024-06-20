package com.cpulsivek.uploadservice.client;

import com.cpulsivek.uploadservice.dto.NotificationDto;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/v1/notification")
public interface NotificationClient {
    @PostExchange("/video")
    void sendNotification(@RequestHeader Map<String, String> headers, @RequestBody NotificationDto notificationDto);
}
