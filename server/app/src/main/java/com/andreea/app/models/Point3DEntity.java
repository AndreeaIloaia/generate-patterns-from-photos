package com.andreea.app.models;

import javax.persistence.*;

@Entity
@Table(name = "point3d", schema = "public")
public class Point3DEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name = "z")
    private double z;

    @ManyToOne
    @JoinColumn(name = "garment_fk")
    private GarmentEntity garmentEntity;

    public Point3DEntity(Long id, Long number, double x, double y, double z, GarmentEntity garmentEntity) {
        this.id = id;
        this.number = number;
        this.x = x;
        this.y = y;
        this.z = z;
        this.garmentEntity = garmentEntity;
    }

    public Point3DEntity(Long number, double x, double y, double z, GarmentEntity garmentEntity) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.z = z;
        this.garmentEntity = garmentEntity;
    }

    public Point3DEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() { return z; }

    public void setZ(double z) { this.z = z; }

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
