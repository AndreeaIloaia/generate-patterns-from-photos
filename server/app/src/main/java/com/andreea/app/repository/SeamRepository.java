package com.andreea.app.repository;

import com.andreea.app.models.SeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeamRepository extends JpaRepository<SeamEntity, Long> {
    List<SeamEntity> findAllByGarmentEntity_Id(Long idGarment);

}
