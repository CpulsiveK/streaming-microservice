package com.cpulsivek.uploadservice.entity;

import jakarta.persistence.*;

@Entity
public class Chunk {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String chunkId;

  private int chunkNumber;

  public String getChunkId() {
    return chunkId;
  }

  public void setChunkId(String chunkId) {
    this.chunkId = chunkId;
  }

  public int getChunkNumber() {
    return chunkNumber;
  }

  public void setChunkNumber(int chunkNumber) {
    this.chunkNumber = chunkNumber;
  }
}
