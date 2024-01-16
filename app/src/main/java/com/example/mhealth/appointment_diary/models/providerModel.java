package com.example.mhealth.appointment_diary.models;

public class providerModel {

    int id, facility_id;
    String full_name, phone_no;

    public providerModel() {
    }

    public providerModel(int id, int facility_id, String full_name, String phone_no) {
        this.id = id;
        this.facility_id = facility_id;
        this.full_name = full_name;
        this.phone_no = phone_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(int facility_id) {
        this.facility_id = facility_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
