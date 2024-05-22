package com.cpulsivek.userservice.dto;

import java.time.OffsetDateTime;

public class ExceptionDto {
  private String uri;
  private String message;
  private OffsetDateTime timeStamp;

  public ExceptionDto(String uri, String message, OffsetDateTime timeStamp) {
    this.uri = uri;
    this.message = message;
    this.timeStamp = timeStamp;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public OffsetDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }
}
