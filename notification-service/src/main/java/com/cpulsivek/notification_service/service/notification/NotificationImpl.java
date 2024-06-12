package com.cpulsivek.notification_service.service.notification;

import com.cpulsivek.notification_service.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationImpl implements Notification {
    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    @Autowired
    public NotificationImpl(KafkaTemplate<String, NotificationDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
