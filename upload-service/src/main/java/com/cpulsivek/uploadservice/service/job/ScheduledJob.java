package com.cpulsivek.uploadservice.service.job;

import com.cpulsivek.uploadservice.entity.Video;
import com.cpulsivek.uploadservice.repository.VideoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

@Service
public class ScheduledJob implements Job {
  private final VideoRepository videoRepository;
  private final S3Client s3Client;
  private final Environment env;

  @Autowired
  public ScheduledJob(VideoRepository videoRepository, S3Client s3Client, Environment env) {
    this.videoRepository = videoRepository;
    this.s3Client = s3Client;
    this.env = env;
  }

  @Scheduled(initialDelay = 5000, fixedDelay = 10000)
  @Override
  public void completeMultipartUploads() {
    List<Video> videos = videoRepository.findAllByIsUploaded(false);

    if (videos.isEmpty()) return;

    List<Video> updatedVideos =
        videos.stream()
            .filter(f -> f.getChunks().size() == f.getTotalChunks())
            .map(
                video -> {
                  CompletedMultipartUpload completedMultipartUpload =
                      completedMultipartUpload(video);

                  CompleteMultipartUploadRequest completeRequest =
                      CompleteMultipartUploadRequest.builder()
                          .bucket(env.getProperty("AWS_BUCKET_NAME"))
                          .key(video.getTitle())
                          .uploadId(video.getUploadId())
                          .multipartUpload(completedMultipartUpload)
                          .build();

                  s3Client.completeMultipartUpload(completeRequest);

                  video.setIsUploaded(true);
                  video.setUrl(getObjectUrl(video.getTitle()));
                  return video;
                })
            .toList();

    videoRepository.saveAll(updatedVideos);
  }

  private CompletedMultipartUpload completedMultipartUpload(Video video) {
    List<CompletedPart> completedParts =
        video.getCompletedParts().stream()
            .map(
                c -> CompletedPart.builder().partNumber(c.getPartNumber()).eTag(c.getTag()).build())
            .toList();

    return CompletedMultipartUpload.builder().parts(completedParts).build();
  }

  private String getObjectUrl(String key) {
    return s3Client
        .utilities()
        .getUrl(GetUrlRequest.builder().bucket(env.getProperty("AWS_BUCKET_NAME")).key(key).build())
        .toExternalForm();
  }
}
