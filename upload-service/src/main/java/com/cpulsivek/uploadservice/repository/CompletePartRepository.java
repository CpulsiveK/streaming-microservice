package com.cpulsivek.uploadservice.repository;

import com.cpulsivek.uploadservice.entity.CompletedPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletePartRepository extends JpaRepository<CompletedPart, Long> {}
