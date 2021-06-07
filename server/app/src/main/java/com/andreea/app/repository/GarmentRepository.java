package com.andreea.app.repository;

import com.andreea.app.models.GarmentEntity;
import com.andreea.app.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface GarmentRepository extends JpaRepository<GarmentEntity, Long> {
//    Optional<GarmentEntity> findByFile_Id(Long idFile);
    Optional<GarmentEntity> findById(Long id);
    List<GarmentEntity> findAllByUser(UserEntity userEntity);
}
