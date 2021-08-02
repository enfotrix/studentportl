package com.enfotrix.studentportal.Models;

public class Model_Result {

    String sub_name;
    String sub_marks;
    String sub_grade;

    public Model_Result() {

    }

    public Model_Result(String sub_name, String sub_marks, String sub_grade) {
        this.sub_name = sub_name;
        this.sub_marks = sub_marks;
        this.sub_grade = sub_grade;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_marks() {
        return sub_marks;
    }

    public void setSub_marks(String sub_marks) {
        this.sub_marks = sub_marks;
    }

    public String getSub_grade() {
        return sub_grade;
    }

    public void setSub_grade(String sub_grade) {
        this.sub_grade = sub_grade;
    }
}
