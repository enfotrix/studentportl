package com.enfotrix.studentportal.Models;

public class Model_Image {
    private String id,path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Model_Image(String id, String path) {
        this.id = id;
        this.path = path;
    }
}
