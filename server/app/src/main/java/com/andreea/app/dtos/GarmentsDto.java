package com.andreea.app.dtos;

import java.util.List;

public class GarmentsDto {
    private List<String> ids;
    private List<String> files;

    public GarmentsDto(List<String> ids, List<String> files) {
        this.ids = ids;
        this.files = files;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
