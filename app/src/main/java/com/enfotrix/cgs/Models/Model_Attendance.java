package com.enfotrix.cgs.Models;

public class Model_Attendance {

    String date;
    String status;

    public Model_Attendance() {

    }

    public Model_Attendance(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
