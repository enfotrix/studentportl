package com.enfotrix.studentportal.Models;

public class Model_Notifi {
    private String id,data,date,heading;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Model_Notifi(String id, String data, String date, String heading) {
        this.id = id;
        this.data = data;
        this.date = date;
        this.heading = heading;
    }
}
