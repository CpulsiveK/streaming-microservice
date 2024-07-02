package com.cpulsivek.notification_service.dto;

public record NotificationDto(String type, String timeStamp, Boolean read, Object data) {}
