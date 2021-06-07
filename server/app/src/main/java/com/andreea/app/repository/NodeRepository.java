package com.andreea.app.repository;

import com.andreea.app.models.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    List<NodeEntity> findAllByGarmentEntity_Id(Long idGarment);
}
