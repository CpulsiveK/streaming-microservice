package com.cpulsivek.uploadservice.service.upload;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface Upload {
    void uploadVideo(MultipartFile file, String title, String description, String duration,
     int totalChunks, int chunkNumber, HttpServletRequest httpServletRequest) throws IOException;
}
