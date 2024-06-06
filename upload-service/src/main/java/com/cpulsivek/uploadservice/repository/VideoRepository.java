package com.cpulsivek.uploadservice.repository;

import com.cpulsivek.uploadservice.entity.Video;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByTitle(String title);

    List<Video> findAllByIsUploaded(Boolean isUploaded);
}
