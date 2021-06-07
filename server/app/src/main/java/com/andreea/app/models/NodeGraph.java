package com.andreea.app.models;

import com.andreea.app.models.Point3DEntity;

import java.util.List;

public class NodeGraph {
    private Point3DEntity coords;
    private List<Long> neighbours;

    public NodeGraph(Point3DEntity coords, List<Long> neighbours) {
        this.coords = coords;
        this.neighbours = neighbours;
    }

    public Point3DEntity getCoords() {
        return coords;
    }

    public void setCoords(Point3DEntity coords) {
        this.coords = coords;
    }

    public List<Long> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Long> neighbours) {
        this.neighbours = neighbours;
    }
}
