package com.cpulsivek.notification_service.controller;

import com.cpulsivek.notification_service.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/notification")
@RestController
@Service
public class NotificationController {
  private final com.cpulsivek.notification_service.service.notification.Notification notification;

  @Autowired
  public NotificationController(
      com.cpulsivek.notification_service.service.notification.Notification notification) {
    this.notification = notification;
  }

  @PostMapping("/video")
  public ResponseEntity<Void> sendUploadNotification(
      @RequestBody NotificationDto notificationDto) {
    notification.sendUploadNotification(notificationDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
