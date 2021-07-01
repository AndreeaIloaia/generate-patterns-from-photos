package com.andreea.app.controller;

import com.andreea.app.service.FileServiceImplementation;
import com.andreea.app.service.GarmentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Controller dedicat pentru gestionarea fisierelor
 */
@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    FileServiceImplementation fileServiceImplementation;
    @Autowired
    GarmentServiceImplementation garmentServiceImplementation;


    public FileController(FileServiceImplementation fileServiceImplementation,
                          GarmentServiceImplementation garmentServiceImplementation) {
        this.fileServiceImplementation = fileServiceImplementation;
        this.garmentServiceImplementation = garmentServiceImplementation;
    }

    /**
     * Salveaza un file trimis prin endpoint
     *
     * @param file - MultipartFile
     * @return FileUploadResponse - contine numele fisierului si uri-ul
     * @throws IOException - daca apare vreo exceptie la salvare
     */
//    @PostMapping("/upload/{typeGarment}")
//    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable String typeGarment) throws Exception {
//        try {
//            FileEntity fileEntity = fileServiceImplementation.upload(file);
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/api/files/")
//                    .path("/download/" + fileEntity.getFileName())
////                .path(String.valueOf(fileEntity.getId()))
//                    .toUriString();
//            try {
//            garmentServiceImplementation.saveGarment(typeGarment, "mama", fileEntity);
//            } catch (Exception e){
//                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
//                        HttpStatus.BAD_REQUEST);
//            }
//            return new ResponseEntity<>(new FileUploadResponse(fileEntity.getFileName(), fileDownloadUri),
//                    HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }

    /**
     * Salveaza mai multe fisiere trimise prin endpoint
     *
     * @param files - lista de MultipartFile
     * @return List of ResponseEntity - daca totul decurge bine, se va returna o lista de FileUploadResponse (numele si uri-ul fiecare fisier
     * - altfel, o lista cu un element de tip ApiResponse cu success - false si mesajul corespunzator
     */
//    @PostMapping("/multiple/upload/{idGarment}")
//    public List<ResponseEntity> multipleUpload(@RequestParam("files") MultipartFile[] files, @PathVariable String idGarment) {
//        //va exista o limita de fisiere care se pot incarca
//        List<ResponseEntity> response = new ArrayList<>();
//
//        if (files.length > 10) {
//            response.add(new ResponseEntity<>(new ApiResponse(false, "Too many files. Try with less than 10!"),
//                    HttpStatus.BAD_REQUEST));
//        } else {
//            Arrays.stream(files)
//                    .forEach(file -> {
//                        try {
//                            FileEntity fileEntity = fileServiceImplementation.upload(file, idGarment);
//                            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                                    .path("/api/files/")
//                                    .path(String.valueOf(fileEntity.getId()))
//                                    .toUriString();
//                            response.add(new ResponseEntity<>(new FileUploadResponse(fileEntity.getFileName(), fileDownloadUri),
//                                    HttpStatus.OK));
//                        } catch (Exception e) {
//                            //TODO - mai vezi aici cum returnezi
//                            response.clear();
//                            response.add(new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
//                                    HttpStatus.BAD_REQUEST));
////                            return response;
//                        }
//                    });
//        }
//
//        return response;
//    }

    /**
     * Descarca un fisier dandu-se numele acestuia
     * @param fileName - String; numele file-ului
     * @param request - HttpServletRequest
     * @return
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadSingleFile(@PathVariable String fileName,  HttpServletRequest request) {
        Resource resource = fileServiceImplementation.download(fileName);
        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        mimeType = mimeType == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }


    /**
     * Descarca mai multe files intr-un zip
     * @param files - vector de String, reprezentand numele fisierelor dorite pentru descarcare
     * @param response - HttpServletResponse
     * @throws IOException - daca apare vreo exceptie la download
     */
    @GetMapping("/zipDownload")
//    public ResponseEntity<Resource> downloadZipFiles(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException {
    public void downloadZipFiles(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            Arrays.stream(files)
                    .forEach(file -> {
                      Resource resource = fileServiceImplementation.download(file);
                        ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));

                        try {
                            zipEntry.setSize(resource.contentLength());
                            zipOutputStream.putNextEntry(zipEntry);

                            StreamUtils.copy(resource.getInputStream(), zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            zipOutputStream.finish();
        }
        response.setStatus(200);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
    }

}
