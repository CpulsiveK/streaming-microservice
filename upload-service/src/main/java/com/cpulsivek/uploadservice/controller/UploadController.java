package com.cpulsivek.uploadservice.controller;

import com.cpulsivek.uploadservice.dto.VideoUploadResponse;
import com.cpulsivek.uploadservice.service.upload.Upload;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/upload")
@RestController
public class UploadController {
  private final Upload upload;

  @Autowired
  public UploadController(Upload upload) {
    this.upload = upload;
  }

  @PostMapping(path = "/video")
  public ResponseEntity<VideoUploadResponse> uploadVideo(
      @RequestParam MultipartFile file,
      @RequestParam String title,
      @RequestParam String description,
      @RequestParam String duration,
      @RequestParam String totalChunks,
      @RequestParam String chunkNumber) throws IOException {
    upload.uploadVideo(file, title, description, duration, Integer.parseInt(totalChunks), Integer.parseInt(chunkNumber));
    upload.setHeaders();
    return new ResponseEntity<>(new VideoUploadResponse("Chunk received"), HttpStatus.OK);
  }
}
