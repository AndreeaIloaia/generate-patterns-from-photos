package com.andreea.app.models;

import javax.persistence.*;

@Entity
@Table(name = "point", schema = "public")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @ManyToOne
    @JoinColumn(name = "pattern_fk")
    private PatternEntity patternEntity;

    public PointEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointEntity() {
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
}
