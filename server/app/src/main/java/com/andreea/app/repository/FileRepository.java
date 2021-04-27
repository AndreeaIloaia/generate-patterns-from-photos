package com.andreea.app.repository;

import com.andreea.app.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;

/**
 * Interfata care implementeaza JpaRepository
 * Persistence layer
 * Lucreaza cu baza de date, cu tabelul file
 */
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByFileNameAndSize(String fileName, int size);

    FileEntity findByFileName(String fileName);
}

