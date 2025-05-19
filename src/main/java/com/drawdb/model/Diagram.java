package com.drawdb.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table
public class Diagram {
    @Id
    private String id = "id";
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
}
