package com.cpulsivek.uploadservice.repository;

import com.cpulsivek.uploadservice.entity.ETag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ETagRepository extends JpaRepository<ETag, Long> {
}
