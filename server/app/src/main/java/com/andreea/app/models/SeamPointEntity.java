package com.andreea.app.models;

import javax.persistence.*;

@Entity
@Table(name = "seam_point3d", schema = "public")
public class SeamPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "point_fk")
    private Point3DEntity point3DEntity;

    @OneToOne
    @JoinColumn(name = "seam_fk")
    private SeamEntity seamEntity;

    public SeamPointEntity(Point3DEntity point3DEntity, SeamEntity seamEntity) {
        this.point3DEntity = point3DEntity;
        this.seamEntity = seamEntity;
    }

    public SeamPointEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point3DEntity getPoint3DEntity() {
        return point3DEntity;
    }

    public void setPoint3DEntity(Point3DEntity point3DEntity) {
        this.point3DEntity = point3DEntity;
    }

    public SeamEntity getSeamEntity() {
        return seamEntity;
    }

    public void setSeamEntity(SeamEntity seamEntity) {
        this.seamEntity = seamEntity;
    }
}
