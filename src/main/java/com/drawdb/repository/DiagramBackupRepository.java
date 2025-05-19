package com.drawdb.repository;

import com.drawdb.model.DiagramBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DiagramBackupRepository extends JpaRepository<DiagramBackup, Long> {
    void deleteByBackupTimeBefore(LocalDateTime time);
    
    DiagramBackup findTopByOrderByBackupTimeDesc();
}