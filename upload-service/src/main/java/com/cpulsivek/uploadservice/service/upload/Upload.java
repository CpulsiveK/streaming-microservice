package com.cpulsivek.uploadservice.service.upload;

import com.cpulsivek.uploadservice.dto.VideoMetadata;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Upload {
    void uploadVideo(MultipartFile file, VideoMetadata videoMetadata, HttpServletRequest httpServletRequest) throws IOException;
}
