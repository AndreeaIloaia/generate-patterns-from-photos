package com.andreea.app.repository;

import com.andreea.app.models.PatternEntity;
import com.andreea.app.models.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, Long> {

}
