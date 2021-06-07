package com.andreea.app.repository;

import com.andreea.app.models.Point3DEntity;
import com.andreea.app.models.SeamEntity;
import com.andreea.app.models.SeamPointEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeamPointRepository  extends JpaRepository<SeamPointEntity, Long> {
    List<SeamPointEntity> findAllByPoint3DEntity(Point3DEntity point3DEntity);
    List<SeamPointEntity> findAllBySeamEntity(SeamEntity seamEntity);
}
