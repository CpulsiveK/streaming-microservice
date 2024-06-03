package com.cpulsivek.uploadservice.service.upload;

import com.cpulsivek.uploadservice.dto.VideoMetaData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface Upload {
    Object uploadVideo(MultipartFile getUserDto, VideoMetaData videoMetaData, HttpServletRequest httpServletRequest);
}
