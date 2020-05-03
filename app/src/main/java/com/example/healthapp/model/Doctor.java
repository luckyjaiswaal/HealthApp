package com.example.healthapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Doctor implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private String introduction;
    private List<Integer> availableTimes = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public Doctor(int id, String firstName, String lastName, String department, String introduction, List<Integer> availableTimes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.introduction = introduction;
        // this.availableTimes = availableTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Integer> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<Integer> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
