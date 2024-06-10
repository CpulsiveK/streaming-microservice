package com.cpulsivek.uploadservice.service.upload;

import com.cpulsivek.uploadservice.client.UserClient;
import com.cpulsivek.uploadservice.dto.User;
import com.cpulsivek.uploadservice.dto.VideoMetadata;
import com.cpulsivek.uploadservice.entity.Chunk;
import com.cpulsivek.uploadservice.entity.CompletedPart;
import com.cpulsivek.uploadservice.entity.Video;
import com.cpulsivek.uploadservice.exception.DuplicateException;
import com.cpulsivek.uploadservice.repository.ChunkRepository;
import com.cpulsivek.uploadservice.repository.CompletePartRepository;
import com.cpulsivek.uploadservice.repository.VideoRepository;
import com.cpulsivek.uploadservice.service.jwt.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Service
public class UploadImpl implements Upload {
  private final S3Client s3Client;
  private final UserClient userClient;
  private final VideoRepository videoRepository;
  private final ChunkRepository chunkRepository;
  private final CompletePartRepository completePartRepository;
  private final Environment env;
  private final Jwt jwt;

  @Autowired
  public UploadImpl(
      S3Client s3Client,
      UserClient userClient,
      VideoRepository videoRepository,
      ChunkRepository chunkRepository,
      CompletePartRepository completePartRepository,
      Environment env,
      Jwt jwt) {
    this.s3Client = s3Client;
    this.userClient = userClient;
    this.videoRepository = videoRepository;
    this.chunkRepository = chunkRepository;
    this.completePartRepository = completePartRepository;
    this.env = env;
    this.jwt = jwt;
  }

  @Override
  public void uploadVideo(
      MultipartFile file,
      String title,
      String description,
      String duration,
      int totalChunks,
      int chunkNumber,
      HttpServletRequest httpServletRequest)
      throws IOException {
    VideoMetadata videoMetadata =
        new VideoMetadata(title, description, duration, totalChunks, chunkNumber);

    Optional<Video> optionalVideo =
        videoRepository.findByTitleAndIsUploaded(videoMetadata.title(), false);

    List<Object> chunk;
    Video video;

    if (optionalVideo.isPresent()) {
      video = optionalVideo.get();
      chunk = saveChunk(videoMetadata, file, video.getUploadId());

      video.setTitle(videoMetadata.title());
      video.setDescription(videoMetadata.description());
      video.setTotalChunks(videoMetadata.totalChunks());
      video.getChunks().add((Chunk) chunk.getFirst());
      video.getCompletedParts().add((CompletedPart) chunk.getLast());
    } else {
      String uploadId = initiateMultipartUpload(videoMetadata, file.getContentType());
      chunk = saveChunk(videoMetadata, file, uploadId);

      video = new Video();
      video.setTitle(videoMetadata.title());
      video.setUploadId(uploadId);
      video.setDescription(videoMetadata.description());
      video.setTotalChunks(videoMetadata.totalChunks());
      video.setCompletedParts(List.of((CompletedPart) chunk.getLast()));
      video.setChunks(List.of((Chunk) chunk.getFirst()));
      video.setUserId(getUserId(httpServletRequest.getHeader("Authorization").substring(7)));
    }
    videoRepository.save(video);
  }

  private String initiateMultipartUpload(VideoMetadata videoMetadata, String contentType) {
    CreateMultipartUploadRequest createRequest =
        CreateMultipartUploadRequest.builder()
            .bucket(env.getProperty("AWS_BUCKET_NAME"))
            .key(videoMetadata.title())
            .contentType(contentType)
            .build();
    CreateMultipartUploadResponse createResponse = s3Client.createMultipartUpload(createRequest);

    return createResponse.uploadId();
  }

  private List<Object> saveChunk(VideoMetadata videoMetadata, MultipartFile file, String uploadId)
      throws IOException {
    Optional<Chunk> optionalChunk = chunkRepository.findByChunkNumber(videoMetadata.chunkNumber());

    if (optionalChunk.isPresent())
      throw new DuplicateException("Chunk" + videoMetadata.chunkNumber() + "already " + "received");

    UploadPartResponse uploadPartResponse = uploadChunkToAws(videoMetadata, file, uploadId);

    Chunk chunk = new Chunk();
    chunk.setChunkId(uploadPartResponse.eTag());
    chunk.setChunkNumber(videoMetadata.chunkNumber());

    CompletedPart completedPart = new CompletedPart();
    completedPart.setTag(uploadPartResponse.eTag());
    completedPart.setPartNumber(videoMetadata.chunkNumber());

    return List.of(chunkRepository.save(chunk), completePartRepository.save(completedPart));
  }

  private UploadPartResponse uploadChunkToAws(
      VideoMetadata videoMetadata, MultipartFile file, String uploadId) throws IOException {
    UploadPartRequest uploadPartRequest =
        UploadPartRequest.builder()
            .bucket(env.getProperty("AWS_BUCKET_NAME"))
            .key(videoMetadata.title())
            .uploadId(uploadId)
            .partNumber(videoMetadata.chunkNumber())
            .contentLength(file.getSize())
            .build();

    return s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(file.getBytes()));
  }

  private Long getUserId(String token) {
    User user = userClient.findByUsername(jwt.extractEmail(token));
    return user.id();
  }
}
