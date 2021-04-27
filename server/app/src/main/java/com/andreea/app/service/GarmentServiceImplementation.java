package com.andreea.app.service;

import com.andreea.app.auth.UserPrincipal;
import com.andreea.app.models.*;
import com.andreea.app.repository.GarmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GarmentServiceImplementation {

    @Autowired
    UserServiceImplementation userServiceImplementation;
    @Autowired
    PatternServiceImplementation patternServiceImplementation;
    @Autowired
    GarmentRepository garmentRepository;

    public void saveGarment(String type, String client, FileEntity fileEntity) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long id = userPrincipal.getId();
        Optional<UserEntity> user = userServiceImplementation.findById(id);

        if(user.isEmpty()) {
            throw new Exception("No user with the specified id.");
        }
        UserEntity userEntity = user.get();

        GarmentEntity garmentEntity = new GarmentEntity();

        garmentEntity.setClient(client);
        for(TypeGarment t: TypeGarment.values()) {
            if (t.name().equals(type.toUpperCase())) {
                garmentEntity.setType(type);
            }
        }
//        garmentEntity.setType(TypeGarment.valueOf(type.toUpperCase()));
//        garmentEntity.setPatterns(patternEntities);
        garmentEntity.setUser(userEntity);
        garmentEntity.setFile(fileEntity);

        garmentRepository.save(garmentEntity);

        List<PatternEntity> patternEntities = new ArrayList<>();
        patternServiceImplementation.save(garmentEntity);
    }


    /**
     * Get the garment for a file and return the list of patterns for it
     * @param id - Long; fileId coresponding to a garment
     * @return - List of PatternEntities
     */
    public List<List<PointEntity>> getPatterns(Long id) {
        GarmentEntity garmentEntity = garmentRepository.findByFile_Id(id).get();
        return patternServiceImplementation.getCoordinatesForAllPatterns(garmentEntity.getId());
    }


}
