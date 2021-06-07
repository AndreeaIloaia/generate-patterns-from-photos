package com.andreea.app.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "garment", schema = "public")
public class GarmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private UserEntity user;

    @JsonInclude()
    @Transient
    private HashMap<Long, NodeGraph> graph;

    public GarmentEntity() {
    }

    public GarmentEntity(Long id, String type) {
        this.id = id;
        this.type = type;
        this.graph = new HashMap<>();
    }

    public GarmentEntity(String type) {
        this.type = type;
        this.graph = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public HashMap<Long, NodeGraph> getGraph() {
        return graph;
    }

    public void setGraph(HashMap<Long, NodeGraph> graph) {
        this.graph = graph;
    }
}
