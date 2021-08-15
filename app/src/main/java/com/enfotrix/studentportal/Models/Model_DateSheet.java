package com.enfotrix.studentportal.Models;

public class Model_DateSheet {

    String day;
    String month;
    String subName;
    String classsectin;
    String classsession;

    public Model_DateSheet() {

    }

    public Model_DateSheet(String day, String month, String subName, String classsectin, String classsession) {
        this.day = day;
        this.month = month;
        this.subName = subName;
        this.classsectin = classsectin;
        this.classsession = classsession;
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

    public String getClasssectin() {
        return classsectin;
    }

    public void setClasssectin(String classsectin) {
        this.classsectin = classsectin;
    }

    public String getClasssession() {
        return classsession;
    }

    public void setClasssession(String classsession) {
        this.classsession = classsession;
    }
}
