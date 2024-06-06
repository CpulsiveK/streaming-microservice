package com.cpulsivek.uploadservice.entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.util.List;

@Table(name = "videos")
@Entity
public class Video {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String title;

  private String description;

  private Duration duration;

  private Long userId;
  private String uploadId;

  private Integer totalChunks;

  @OneToMany(targetEntity = ETag.class)
  private List<String> eTags;

  @OneToMany(targetEntity = Chunk.class, fetch = FetchType.LAZY)
  private List<Chunk> chunks;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public Integer getTotalChunks() {
    return totalChunks;
  }

  public void setTotalChunks(Integer totalChunks) {
    this.totalChunks = totalChunks;
  }

  public List<Chunk> getChunks() {
    return chunks;
  }

  public void setChunks(List<Chunk> chunks) {
    this.chunks = chunks;
  }

  public String getUploadId() {
    return uploadId;
  }

  public void setUploadId(String uploadId) {
    this.uploadId = uploadId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
