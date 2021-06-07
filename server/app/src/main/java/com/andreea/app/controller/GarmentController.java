package com.andreea.app.controller;

import com.andreea.app.dtos.ApiResponse;
import com.andreea.app.dtos.FileUploadResponse;
import com.andreea.app.dtos.GarmentTypeDto;
import com.andreea.app.dtos.GarmentsDto;
import com.andreea.app.models.FileEntity;
import com.andreea.app.models.GarmentEntity;
import com.andreea.app.models.GraphDto;
import com.andreea.app.models.PointEntity;
import com.andreea.app.service.FileServiceImplementation;
import com.andreea.app.service.GarmentServiceImplementation;
import com.andreea.app.service.PatternServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
     *
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
     *
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

    /**
     * Metoda pentru clasificarea unei imagini
     *
     * @return list: List<PointEntity>
     */
    @PostMapping("/upload")
    public ResponseEntity getGarmentType(@RequestParam("file") MultipartFile files[]) throws Exception {
        List<ResponseEntity> response = new ArrayList<>();
        GarmentTypeDto garmentTypeDto = null;
        if (files.length > 2) {
            response.add(new ResponseEntity<>(new ApiResponse(false, "Too many files. Try with less than 10!"),
                    HttpStatus.BAD_REQUEST));
        } else {
            GarmentEntity garmentEntity = garmentServiceImplementation.saveGarment("empty");
            //facem clasificare pe o singura poza
//            garmentTypeDto = garmentServiceImplementation.getType(files[0], id);
            int index = 0;
            for (MultipartFile f : files) {
                try {
                    FileEntity fileEntity = fileServiceImplementation.upload(f, garmentEntity);
                    if(index == 0) {
                        garmentTypeDto = garmentServiceImplementation.getType(fileEntity, garmentEntity.getId());
                        index++;
                    }
                    System.out.println();
                } catch (Exception e) {
                    return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                            HttpStatus.BAD_REQUEST);
                }
            }
        }
        return ResponseEntity.ok().body(garmentTypeDto);
    }

    /**
     * Metoda pentru obtinerea punctelor 3D corespunzatoare unei piese de imbracaminte
     *
     * @return list: List<PointEntity>
     */
    @PostMapping("/get-3d-graph")
    public ResponseEntity get3DGraph(@Valid @RequestBody GraphDto graph) {
        try {
            garmentServiceImplementation.saveGraph(graph);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } finally {
            System.out.println("finally");
        }
    }

    /**
     * Metoda pentru incarcarea punctelor 3D corespunzatoare unei piese de imbracaminte din baza de date
     * si trimiterea pe front pentru modelul dorit
     *
     * @return list: List<PointEntity>
     */
    @GetMapping("/load-3d-graph/{idGarment}/{idOption}")
    public ResponseEntity load3DGraph(@PathVariable String idGarment, @PathVariable String idOption) {
        try {
            GraphDto graph = garmentServiceImplementation.loadGraph(idGarment, idOption);
            return ResponseEntity.ok().body(graph);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Metoda pentru obtinerea tuturor pieselor vestimentare ale unui utilizator
     *
     * @return list: List<PointEntity>
     */
    @GetMapping("get-images")
    public ResponseEntity getGarments() {
        try {
            GarmentsDto garmentsDto = garmentServiceImplementation.getGarments();
            return ResponseEntity.ok().body(garmentsDto);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
