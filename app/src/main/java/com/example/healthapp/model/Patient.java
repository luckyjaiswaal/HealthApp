package com.example.healthapp.model;

import java.io.Serializable;

public class Patient implements Serializable {
    private String Email = "";
    private String FirstName = "";
    private String LastName = "";
    private String Role = "";
    private String PatientKey="";

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getPatientKey() {
        return PatientKey;
    }

    public void setPatientKey(String patientKey) {
        PatientKey = patientKey;
    }
}

