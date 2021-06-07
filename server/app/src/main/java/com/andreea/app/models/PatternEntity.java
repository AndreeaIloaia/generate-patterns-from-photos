package com.andreea.app.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pattern", schema = "public")
public class PatternEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "garment_fk")
    private GarmentEntity garmentEntity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pattern_fk")
    private List<PointEntity> coordinates = new ArrayList<>();

    public PatternEntity(Long id, String name, String type, List<PointEntity> coordinates) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    public PatternEntity(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public PatternEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PointEntity> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<PointEntity> coordinates) {
        this.coordinates = coordinates;
    }

    public GarmentEntity getGarmentEntity() {
        return garmentEntity;
    }

    public void setGarmentEntity(GarmentEntity garmentEntity) {
        this.garmentEntity = garmentEntity;
    }
}
