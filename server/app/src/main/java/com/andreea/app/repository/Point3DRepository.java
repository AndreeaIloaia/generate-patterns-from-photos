package com.andreea.app.repository;

import com.andreea.app.models.GarmentEntity;
import com.andreea.app.models.Point3DEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Point3DRepository extends JpaRepository<Point3DEntity, Long> {
    List<Point3DEntity> findAllByGarmentEntity_Id(Long idGarment);

    Optional<Point3DEntity> findByNumberAndGarmentEntity(Long number, GarmentEntity garmentEntity);
}
