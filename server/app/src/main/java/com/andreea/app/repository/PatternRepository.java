package com.andreea.app.repository;

import com.andreea.app.models.GarmentEntity;
import com.andreea.app.models.PatternEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatternRepository extends JpaRepository<PatternEntity, Long> {
    List<PatternEntity> findAllByGarmentEntity_Id(Long id);
}
