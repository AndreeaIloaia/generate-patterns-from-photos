package com.andreea.app.models;

import javax.persistence.*;

/**
 * Modelul pentru fisiere
 */
@Entity
@Table(name="file", schema = "public")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "filename")
    private String fileName;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "size")
    private int size;

    public FileEntity() {
    }

    public FileEntity(Long id, String fileName, byte[] content, int size) {
        this.id = id;
        this.fileName = fileName;
        this.content = content;
        this.size = size;
    }

    public FileEntity(String fileName, byte[] content, int size) {
        this.fileName = fileName;
        this.content = content;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
