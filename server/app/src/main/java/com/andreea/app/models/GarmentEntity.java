package com.andreea.app.models;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "garment", schema = "public")
public class GarmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "client")
    private String client;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "file_fk")
    private FileEntity file;

    //TODO - vezi de ce iti pune coloana asta de garment_fk in tabelul garment....
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "garment_fk")
    private List<PatternEntity> patterns = new ArrayList<>();


    public GarmentEntity() {
    }

    public GarmentEntity(Long id, String type, String client, List<PatternEntity> patterns) {
        this.id = id;
        this.type = type;
        this.client = client;
        this.patterns = patterns;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<PatternEntity> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<PatternEntity> patterns) {
        this.patterns = patterns;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}
