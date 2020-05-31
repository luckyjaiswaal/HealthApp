package com.example.healthapp.model;

import java.io.Serializable;

public class Doctor implements Serializable {
    private String FirstName;
    private String LastName;
    private String Department;
    private String Introduction;
    private String Gender;
    private String Email;
    private String DoctorKey="";
    private String DoctorId;

    public String getDoctorKey() {
        return DoctorKey;
    }

    public void setDoctorKey(String doctorKey) {
        DoctorKey = doctorKey;
    }
    private double lat;
    private double lng;

    public Doctor() {}

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        this.Introduction = introduction;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }
}
