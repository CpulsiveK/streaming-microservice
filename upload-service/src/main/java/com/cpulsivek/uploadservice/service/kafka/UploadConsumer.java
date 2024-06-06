package com.cpulsivek.uploadservice.service.kafka;

import com.cpulsivek.uploadservice.dto.VideoMetadata;
import com.cpulsivek.uploadservice.service.upload.Upload;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UploadConsumer {
  private final Upload upload;
  private final HttpServletRequest httpServletRequest;

  private final Set<VideoMetadata> data = new HashSet<>();

  public UploadConsumer(Upload upload, HttpServletRequest httpServletRequest) {
    this.upload = upload;
    this.httpServletRequest = httpServletRequest;
  }

  @KafkaListener(topics = "upload", groupId = "test")
  public void consumeUpload(VideoMetadata videoMetaData) {
    while (data.size() < videoMetaData.totalChunks()) {
      data.add(videoMetaData);
    }

    System.out.println();
  }
}
