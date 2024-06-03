package com.cpulsivek.uploadservice.dto;

import java.time.OffsetDateTime;

public record ExceptionDto(String uri, String message, OffsetDateTime timeStamp) {}
