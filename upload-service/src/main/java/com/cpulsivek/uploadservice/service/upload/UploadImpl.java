package com.cpulsivek.uploadservice.service.upload;

import com.cpulsivek.uploadservice.client.UserClient;
import com.cpulsivek.uploadservice.dto.VideoMetaData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadImpl implements Upload {
  private final UserClient userClient;

  @Autowired
  public UploadImpl(UserClient userClient) {
    this.userClient = userClient;
  }

  @Override
  public Object uploadVideo(MultipartFile multipartFile, VideoMetaData videoMetaData, HttpServletRequest httpServletRequest) {
    return videoMetaData;
  }
}
