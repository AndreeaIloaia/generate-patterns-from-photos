package com.andreea.app.controller;

import com.andreea.app.dtos.ApiResponse;
import com.andreea.app.models.PointEntity;
import com.andreea.app.service.FileServiceImplementation;
import com.andreea.app.service.GarmentServiceImplementation;
import com.andreea.app.service.PatternServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller dedicat entitatilor tipar
 */
@CrossOrigin
@RestController
@RequestMapping(path = "api")
public class GarmentController {
    private final PatternServiceImplementation patternServiceImplementation;
    private final FileServiceImplementation fileServiceImplementation;
    private final GarmentServiceImplementation garmentServiceImplementation;

    @Autowired
    public GarmentController(PatternServiceImplementation patternServiceImplementation, FileServiceImplementation fileServiceImplementation, GarmentServiceImplementation garmentServiceImplementation) {
        this.patternServiceImplementation = patternServiceImplementation;
        this.fileServiceImplementation = fileServiceImplementation;
        this.garmentServiceImplementation = garmentServiceImplementation;
    }

    /**
     * Metoda pentru trimiterea coordonatelor cheie pentru desenarea unui tipar
     * @return list: List<PointEntity>
     */
    @GetMapping("/patterns")
    public ResponseEntity<List<PointEntity>> getPatterns() {
//        List<PointEntity> list = patternServiceImplementation.getCoordinates();
//        return ResponseEntity.ok().body(list);
        return ResponseEntity.ok().build();
    }

    /**
     * Metoda pentru trimiterea coordonatelor cheie pentru desenarea unui tipar
     * @return list: List<PointEntity>
     */
    @GetMapping("/patterns/{fileName}")
    public ResponseEntity getPatterns(@PathVariable String fileName) {
        try {
            List<List<PointEntity>> list = new ArrayList<>();
            Long fileId = fileServiceImplementation.findByName(fileName);
            list = garmentServiceImplementation.getPatterns(fileId);
            return ResponseEntity.ok().body(list);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
