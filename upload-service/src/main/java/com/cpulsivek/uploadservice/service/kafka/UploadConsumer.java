package com.cpulsivek.uploadservice.service.kafka;

import com.cpulsivek.uploadservice.service.upload.Upload;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UploadConsumer {
    private final Upload upload;

    public UploadConsumer(Upload upload) {
        this.upload = upload;
    }

  @KafkaListener(topics = "upload")
  public void consumeUpload() {
    /* TODO implement logic to upload video after consuming topic */
  }
}
