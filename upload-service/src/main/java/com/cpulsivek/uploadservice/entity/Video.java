package com.cpulsivek.uploadservice.entity;

import jakarta.persistence.*;

import java.time.Duration;

@Table(name = "videos")
@Entity
public class Video {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String description;

  private Duration duration;
}
