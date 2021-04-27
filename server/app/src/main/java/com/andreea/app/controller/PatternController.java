package com.andreea.app.controller;

import com.andreea.app.dtos.ApiResponse;
import com.andreea.app.service.FileServiceImplementation;
import com.andreea.app.service.GarmentServiceImplementation;
import com.andreea.app.service.PatternServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicat entitatilor tipar
 */
@CrossOrigin
@RestController
@RequestMapping(path = "api")
public class PatternController {
    private final PatternServiceImplementation patternServiceImplementation;
    private final FileServiceImplementation fileServiceImplementation;
    private final GarmentServiceImplementation garmentServiceImplementation;

    @Autowired
    public PatternController(PatternServiceImplementation patternServiceImplementation, FileServiceImplementation fileServiceImplementation, GarmentServiceImplementation garmentServiceImplementation) {
        this.patternServiceImplementation = patternServiceImplementation;
        this.fileServiceImplementation = fileServiceImplementation;
        this.garmentServiceImplementation = garmentServiceImplementation;
    }

}
