package com.cpulsivek.uploadservice.service.job;

import com.cpulsivek.uploadservice.entity.Video;
import com.cpulsivek.uploadservice.repository.ChunkRepository;
import com.cpulsivek.uploadservice.repository.VideoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;

@Service
public class ScheduledJob implements Job {
  private final VideoRepository videoRepository;
  private final ChunkRepository chunkRepository;
  private final S3Client s3Client;
  private final Environment env;

  @Autowired
  public ScheduledJob(
      VideoRepository videoRepository,
      ChunkRepository chunkRepository,
      S3Client s3Client,
      Environment env) {
    this.videoRepository = videoRepository;
    this.chunkRepository = chunkRepository;
    this.s3Client = s3Client;
    this.env = env;
  }

  @Override
  public void completeMultipartUploads() {
    List<Video> videos = videoRepository.findAllByIsUploaded(false);

    for (Video video : videos) {
      if (video.getChunks().size() != video.getTotalChunks()
          || video.getCompletedParts().size() != video.getTotalChunks()) continue;

      List<CompletedPart> completedParts =
          video.getCompletedParts().stream()
              .map(
                  c ->
                      CompletedPart.builder()
                          .partNumber(c.getPartNumber())
                          .eTag(c.getTag())
                          .build())
              .toList();

      CompletedMultipartUpload completedMultipartUpload =
          CompletedMultipartUpload.builder().parts(completedParts).build();

      CompleteMultipartUploadRequest completeRequest =
          CompleteMultipartUploadRequest.builder()
              .bucket(env.getProperty("AWS_BUCKET_NAME"))
              .key(video.getTitle())
              .uploadId(video.getUploadId())
              .multipartUpload(completedMultipartUpload)
              .build();

      s3Client.completeMultipartUpload(completeRequest);

      video.setIsUploaded(true);
      //      video.url(); work on the url generation later

      videoRepository.save(video);
    }
  }
}
