package com.cpulsivek.uploadservice.repository;

import com.cpulsivek.uploadservice.entity.Chunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChunkRepository extends JpaRepository<Chunk, Long> {
    Optional<Chunk> findByChunkNumber(Integer chunkId);
}
