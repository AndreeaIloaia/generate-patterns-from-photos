package com.andreea.app.controller;

import com.andreea.app.dtos.ApiResponse;
import com.andreea.app.dtos.GarmentsDto;
import com.andreea.app.models.GarmentEntity;
import com.andreea.app.models.GraphDto;
import com.andreea.app.service.FileServiceImplementation;
import com.andreea.app.service.GarmentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller dedicat entitatilor tipar
 */
@CrossOrigin
@RestController
@RequestMapping(path = "api")
public class GarmentController {
    private final GarmentServiceImplementation garmentServiceImplementation;

    @Autowired
    public GarmentController(GarmentServiceImplementation garmentServiceImplementation) {
        this.garmentServiceImplementation = garmentServiceImplementation;
    }


    /**
     * Metoda pentru salvarea unei piese de imbracaminte si fisierul sau fisierele corespunzatoare
     *
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity uploadGarment(@RequestParam("file") MultipartFile files[]) throws Exception {
        List<ResponseEntity> response = new ArrayList<>();
        if (files.length > 2) {
            response.add(new ResponseEntity<>(new ApiResponse(false, "Too many files. Try with less than 10!"),
                    HttpStatus.BAD_REQUEST));
        } else {
            GarmentEntity garmentEntity = garmentServiceImplementation.saveGarment("empty");
            boolean isOk = garmentServiceImplementation.saveFilesForGarment(garmentEntity, files);
            if(isOk) {
                return ResponseEntity.ok().body(garmentEntity.getId());
            }
        }
        return ResponseEntity.ok().body("Nothing happened.");
    }

    /**
     * Metoda pentru obtinerea punctelor 3D corespunzatoare unei piese de imbracaminte
     *
     * @return
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
     * @return
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
     * @return
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
