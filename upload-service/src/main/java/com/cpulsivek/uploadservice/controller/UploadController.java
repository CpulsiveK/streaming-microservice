package com.cpulsivek.uploadservice.controller;

import com.cpulsivek.uploadservice.dto.VideoMetadata;
import com.cpulsivek.uploadservice.service.upload.Upload;
import jakarta.servlet.http.HttpServletRequest;
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
  public ResponseEntity<String> uploadVideo(
      @RequestPart MultipartFile file,
      @RequestPart VideoMetadata videoMetadata,
      HttpServletRequest httpServletRequest) {
    upload.uploadVideo(file, videoMetadata, httpServletRequest);
    return new ResponseEntity<>("Chunk received", HttpStatus.OK);
  }
}
