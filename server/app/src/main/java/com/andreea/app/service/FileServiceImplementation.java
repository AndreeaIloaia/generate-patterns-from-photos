package com.andreea.app.service;

import com.andreea.app.models.FileEntity;
import com.andreea.app.models.GarmentEntity;
import com.andreea.app.repository.FileRepository;
import com.andreea.app.repository.GarmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Clasa menita sa acopere nevoiele din business layer pentru fisiere
 */
@Service
public class FileServiceImplementation {
    private final FileRepository fileRepository;
    private Path fileStoragePath;
    @Value("${file.storage.location}")
    private String fileStorageLocation;


    @Autowired
    public FileServiceImplementation(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Creaza daca nu exista un fisier pentru stocarea si gestionarea fisierelor din aplicatie
     * arunca exceptie daca exista vreo problema in crearea fisierului
     */
    private void setFileStoragePath() {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }


    /**
     * Metoda pentru incarcarea unui file atat in baza de date, cat si in file Storage
     * @param file - MultipartFile; fisierul care trebuie salvat
     * @return FileEntity - entitatea salvata
     * @throws Exception - daca fisierul exista deja sau daca intampina vreo problema la salvare
     */
    //TODO - erori, specificatii etc
    @Transactional
    public FileEntity upload(MultipartFile file, GarmentEntity garmentEntity) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        int size = file.getBytes().length;

        //salveaza file in baza de date
        FileEntity searchFile = fileRepository.findByFileNameAndSize(fileName, size);

        if (searchFile == null) {
            FileEntity ff = new FileEntity(fileName, file.getBytes(), size, garmentEntity);
            searchFile = fileRepository.save(ff);
        } else {
            throw new Exception("File already exists");
        }

        //salveaza file intr-un director
        setFileStoragePath();
        Path filePath = Paths.get(fileStoragePath + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }

        return searchFile;
    }

    /**
     * Metoda pentru download al unui file, dupa nume, luat din fileStorage
     * @param fileName - String; numele file-ului
     * @return Resource - file-ul cu numele dat ca parametru
     *          exceptie - in cazul in care nu exista sau nu poate fi citit
     */
    public Resource download(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());

        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public Long findByName(String fileName) throws Exception {
        FileEntity file = fileRepository.findByFileName(fileName);
        if(file == null) {
            throw new Exception("No such file with this name");
        }

        return file.getId();
    }

    public String getFileNameForAGivenGarment(Long idGarment) {
        FileEntity fileEntity = fileRepository.findFirstByGarmentEntityId(idGarment);
        return fileEntity.getFileName();
    }
}
