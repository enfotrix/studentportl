package com.enfotrix.cgs.Models;

public class Model_TimeTable {

    String tv_slotNumber;
    String tv_subName;
    String tv_TeacherName;
    String tv_startTime;
    String tv_endTime;

    public Model_TimeTable() {

    }

    public Model_TimeTable(String tv_slotNumber, String tv_subName, String tv_TeacherName, String tv_startTime, String tv_endTime) {
        this.tv_slotNumber = tv_slotNumber;
        this.tv_subName = tv_subName;
        this.tv_TeacherName = tv_TeacherName;
        this.tv_startTime = tv_startTime;
        this.tv_endTime = tv_endTime;
    }

    public String getTv_slotNumber() {
        return tv_slotNumber;
    }

    public void setTv_slotNumber(String tv_slotNumber) {
        this.tv_slotNumber = tv_slotNumber;
    }

    public String getTv_subName() {
        return tv_subName;
    }

    public void setTv_subName(String tv_subName) {
        this.tv_subName = tv_subName;
    }

    public String getTv_TeacherName() {
        return tv_TeacherName;
    }

    public void setTv_TeacherName(String tv_TeacherName) {
        this.tv_TeacherName = tv_TeacherName;
    }

    public String getTv_startTime() {
        return tv_startTime;
    }

    public void setTv_startTime(String tv_startTime) {
        this.tv_startTime = tv_startTime;
    }

    public String getTv_endTime() {
        return tv_endTime;
    }

    public void setTv_endTime(String tv_endTime) {
        this.tv_endTime = tv_endTime;
    }
}
