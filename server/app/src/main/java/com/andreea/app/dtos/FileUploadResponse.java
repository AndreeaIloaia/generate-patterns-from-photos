package com.andreea.app.dtos;

/**
 * Dto pentru un response in urma incarcarii de fisiere
 */
public class FileUploadResponse {
    private String fileName;
    private String url;

    public FileUploadResponse(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
