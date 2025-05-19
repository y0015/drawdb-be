package com.drawdb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table
public class DiagramBackup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String diagramId;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private LocalDateTime backupTime;
}