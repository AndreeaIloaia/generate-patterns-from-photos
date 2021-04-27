package com.andreea.app.service;

import com.andreea.app.models.GarmentEntity;
import com.andreea.app.models.PatternEntity;
import com.andreea.app.models.PointEntity;
import com.andreea.app.repository.PatternRepository;
import com.andreea.app.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatternServiceImplementation {
    @Autowired
    PatternRepository patternRepository;
    @Autowired
    PointServiceImplementation pointServiceImplementation;

    /**
     * Functia genereaza o lista de tipare, fiecare avand un id, (poate nume), tip (fata/spate) si
     * o lista de coordonate
     * Momentan o sa se creeze un singur tipar, deci lista va avea un element
     * Aici se asteapta pentru functia bazinga
     *
     * @param garmentEntity
     * @return List<PatternEntity>
     */
    public List<PatternEntity> save(GarmentEntity garmentEntity) {
        //TODO - vezi de bazinga function

        List<PatternEntity> list = new ArrayList<>();

        PatternEntity patternEntity = new PatternEntity();

        patternEntity.setName(garmentEntity.getType() + "_pattern" + (Math.random() * 100));
        patternEntity.setType("FATA");
        patternEntity.setCoordinates(pointServiceImplementation.getCoordinates(patternEntity));
        patternEntity.setGarmentEntity(garmentEntity);

        patternRepository.save(patternEntity);

        list.add(patternEntity);
        return list;
    }

    /**
     * Get all coordinates for each pattern from a list
     *
     * @param id - Long; id of the specific garment
     * @return List of List of PointsEntity
     */
    public List<List<PointEntity>> getCoordinatesForAllPatterns(Long id) {
        List<List<PointEntity>> listOfCoordinates = new ArrayList<>();
        List<PatternEntity> patternEntityList = patternRepository.findAllByGarmentEntity_Id(id);
        for (PatternEntity p : patternEntityList) {
            listOfCoordinates.add(p.getCoordinates());
        }
        return listOfCoordinates;
    }


}
