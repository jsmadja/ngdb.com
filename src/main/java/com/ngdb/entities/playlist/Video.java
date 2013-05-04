package com.ngdb.entities.playlist;


public class Video {
    private Uploader uploader;
    private String id;
    private String title;

    public Video(Uploader uploader, String id, String title) {
        this.uploader = uploader;
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", uploader='" + uploader + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Uploader getUploader() {
        return uploader;
    }

    public String getUploaderUrl() {
        return uploader.getUploaderUrl();
    }

    public String getUploaderName() {
        return uploader.getDisplayName();
    }
}