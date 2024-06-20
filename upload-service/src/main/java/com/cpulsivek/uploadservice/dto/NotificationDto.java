package com.cpulsivek.uploadservice.dto;

import com.cpulsivek.uploadservice.entity.Video;

public record NotificationDto(String type, String timeStamp, boolean read, Video data) {}
