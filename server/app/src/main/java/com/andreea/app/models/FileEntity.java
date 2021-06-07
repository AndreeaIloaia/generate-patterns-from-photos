package com.andreea.app.models;

import javax.persistence.*;

/**
 * Modelul pentru fisiere
 */
@Entity
@Table(name="file", schema = "public")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "filename")
    private String fileName;

    @Transient
    private byte[] content;

    @Column(name = "size")
    private int size;

    @ManyToOne
    @JoinColumn(name = "garment_fk")
    private GarmentEntity garmentEntity;


    public FileEntity() {
    }

    public FileEntity(Long id, String fileName, byte[] content, int size) {
        this.id = id;
        this.fileName = fileName;
        this.content = content;
        this.size = size;
    }

    public FileEntity(String fileName, byte[] content, int size, GarmentEntity garmentEntity) {
        this.fileName = fileName;
        this.content = content;
        this.size = size;
        this.garmentEntity = garmentEntity;
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

    public GarmentEntity getGarmentEntity() { return garmentEntity; }

    public void setGarmentEntity(GarmentEntity garmentEntity) { this.garmentEntity = garmentEntity; }
}
