package com.cpulsivek.uploadservice.service.kafka;

import com.cpulsivek.uploadservice.dto.VideoMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UploadProducer {
  private final KafkaTemplate<String, VideoMetadata> kafkaTemplate;

  @Autowired
  public UploadProducer(KafkaTemplate<String, VideoMetadata> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendVideoMetadata(VideoMetadata videoMetaData) {
    Message<VideoMetadata> message =
        MessageBuilder.withPayload(videoMetaData).setHeader(KafkaHeaders.TOPIC, "upload").build();
    kafkaTemplate.send(message);
  }
}
