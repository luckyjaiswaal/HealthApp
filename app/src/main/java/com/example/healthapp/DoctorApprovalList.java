package com.example.healthapp;

public class DoctorApprovalList {

    private String FirstName,LastName;

    public DoctorApprovalList(){

    }

    public DoctorApprovalList(String firstName, String lastName) {

        FirstName = firstName;
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }
    public String getLastName(){return LastName;}

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public void setLastName(String lastName){LastName=lastName;}



}
