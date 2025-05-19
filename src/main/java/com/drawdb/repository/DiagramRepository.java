package com.drawdb.repository;

import com.drawdb.model.Diagram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagramRepository extends JpaRepository<Diagram, String> {
}