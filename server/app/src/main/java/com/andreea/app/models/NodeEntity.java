package com.andreea.app.models;

import javax.persistence.*;

@Entity
@Table(name = "nodes", schema = "public")
public class NodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_point")
    private Long start;

    @Column(name = "end_point")
    private Long end;

    @ManyToOne
    @JoinColumn(name = "garment_fk")
    private GarmentEntity garmentEntity;


    public NodeEntity() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public GarmentEntity getGarmentEntity() {
        return garmentEntity;
    }

    public void setGarmentEntity(GarmentEntity garmentEntity) {
        this.garmentEntity = garmentEntity;
    }
}
