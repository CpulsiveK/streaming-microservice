package com.cpulsivek.notification_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
public class Notification {
  @Id private String id;

  private String type;

  private String timeStamp;

  private Boolean read = false;

  private Object data;

  public Notification(String id, String type, String timeStamp, Boolean read, String data) {
    this.id = id;
    this.type = type;
    this.timeStamp = timeStamp;
    this.read = read;
    this.data = data;
  }

  public Notification(String type, String timeStamp, Boolean read, Object data) {
    this.type = type;
    this.timeStamp = timeStamp;
    this.read = read;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    this.read = read;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
