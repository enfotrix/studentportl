package com.enfotrix.cgs.Models;

public class Model_Fee {

    String month;
    String fee;
    String status;

    public Model_Fee() {

    }

    public Model_Fee(String month, String fee, String status) {
        this.month = month;
        this.fee = fee;
        this.status = status;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
