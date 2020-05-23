package com.example.healthapp.model;

public class DSModel {
    int type; // 1-patient, 2-pharmacy
    Doctor doctor;
    double lat;
    double lng;

    public DSModel(int type, double lat, double lng, Doctor doctor) {
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.doctor = doctor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
