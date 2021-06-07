package com.andreea.app.models;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class GraphDto {
    @NotBlank
    private String id;
    private List<List<Double>> vertices;
    private List<List<Long>> edges;
    private List<List<Long>> seams;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<List<Double>> getVertices() {
        return vertices;
    }

    public void setVertices(List<List<Double>> vertices) {
        this.vertices = vertices;
    }

    public List<List<Long>> getEdges() {
        return edges;
    }

    public void setEdges(List<List<Long>> edges) {
        this.edges = edges;
    }

    public List<List<Long>> getSeams() { return seams; }

    public void setSeams(List<List<Long>> seams) { this.seams = seams; }
}
