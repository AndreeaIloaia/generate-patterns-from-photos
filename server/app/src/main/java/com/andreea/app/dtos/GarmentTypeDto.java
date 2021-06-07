package com.andreea.app.dtos;

import java.util.List;

public class GarmentTypeDto {
    private Long id;
    private List<String> types;

    public GarmentTypeDto(Long id, List<String> types) {
        this.id = id;
        this.types = types;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
