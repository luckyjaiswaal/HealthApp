package com.example.healthapp.model;

import java.io.Serializable;

public class Booking implements Serializable {
    private Doctor doctor;
    private String patientId;
    private String bookingId;
    private String date;
    private int timeslot;
    private String note;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
