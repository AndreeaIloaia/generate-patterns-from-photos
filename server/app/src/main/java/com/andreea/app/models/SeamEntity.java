package com.andreea.app.models;

import javax.persistence.*;

@Entity
@Table(name = "seam", schema = "public")
public class SeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_seam")
    private Long number;

    @ManyToOne
    @JoinColumn(name = "garment_fk")
    private GarmentEntity garmentEntity;

    public SeamEntity(Long number, GarmentEntity garmentEntity) {
        this.number = number;
        this.garmentEntity = garmentEntity;
    }

    public SeamEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public GarmentEntity getGarmentEntity() {
        return garmentEntity;
    }

    public void setGarmentEntity(GarmentEntity garmentEntity) {
        this.garmentEntity = garmentEntity;
    }
}
