package com.cpulsivek.notification_service.service.notification;

import com.cpulsivek.notification_service.dto.NotificationDto;

public interface Notification {
  void sendUploadNotification(
      NotificationDto notificationDto);
}
