package com.ngdb.entities.playlist;


public class Uploader {

    private String uploaderId;
    private String displayName;
    private String uploaderUrl;

    public Uploader(String uploaderId, String displayName, String
            uploaderUrl) {
        this.uploaderId = uploaderId;
        this.displayName = displayName;
        this.uploaderUrl = uploaderUrl;
    }

    @Override
    public String toString() {
        return "Uploader{" +
                "displayName='" + displayName + '\'' +
                ", uploaderId='" + uploaderId + '\'' +
                ", uploaderUrl='" + uploaderUrl + '\'' +
                '}';
    }

    public String getUploaderUrl() {
        return uploaderUrl;
    }

    public String getDisplayName() {
        return displayName;
    }
}
