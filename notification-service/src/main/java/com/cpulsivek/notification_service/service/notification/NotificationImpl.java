package com.cpulsivek.notification_service.service.notification;

import com.cpulsivek.notification_service.dto.NotificationDto;
import com.cpulsivek.notification_service.repository.NotificationRepository;
import com.cpulsivek.notification_service.service.kafka.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationImpl implements Notification {
  private final NotificationProducer notificationProducer;
  private final NotificationRepository notificationRepository;

  @Autowired
  public NotificationImpl(
      NotificationProducer notificationProducer, NotificationRepository notificationRepository) {
    this.notificationProducer = notificationProducer;
    this.notificationRepository = notificationRepository;
  }

  @Override
  public void sendUploadNotification(NotificationDto notificationDto) {
    com.cpulsivek.notification_service.model.Notification notification =
        notificationRepository.save(
            new com.cpulsivek.notification_service.model.Notification(
                notificationDto.type(),
                notificationDto.timeStamp(),
                notificationDto.read() != null && notificationDto.read(),
                notificationDto.data()));

    notificationProducer.sendNotification(notification);
  }
}
