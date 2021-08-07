package com.enfotrix.studentportal.Models;

public class Model_DateSheet {

    String day;
    String month;
    String subName;
    String startTime;
    String endTime;

    public Model_DateSheet() {

    }

    public Model_DateSheet(String day, String month, String subName, String startTime, String endTime) {
        this.day = day;
        this.month = month;
        this.subName = subName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
