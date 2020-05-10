package com.example.healthapp.model;

public class Booking {
    private Doctor doctor;
    private String date;
    private int timeslot;
    private String note;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
