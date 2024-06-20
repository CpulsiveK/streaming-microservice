package com.cpulsivek.notification_service.service.kafka;

import com.cpulsivek.notification_service.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
  private final KafkaTemplate<String, Notification> kafkaTemplate;

  @Autowired
  public NotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendNotification(Notification notification) {
    Message<Notification> message =
        MessageBuilder.withPayload(notification)
            .setHeader(KafkaHeaders.TOPIC, notification.getType())
            .build();

    kafkaTemplate.send(message);
  }
}
