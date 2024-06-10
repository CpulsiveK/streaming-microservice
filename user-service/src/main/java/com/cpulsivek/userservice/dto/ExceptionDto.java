package com.cpulsivek.userservice.dto;

import java.time.OffsetDateTime;

public record ExceptionDto(String uri, String message, OffsetDateTime timeStamp) {}
