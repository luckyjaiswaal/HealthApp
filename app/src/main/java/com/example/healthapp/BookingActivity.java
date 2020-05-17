package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.model.Booking;
import com.example.healthapp.model.Doctor;
import com.example.healthapp.model.TimeSlot;
import com.example.healthapp.util.PopupUtil;
import com.example.healthapp.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BookingActivity extends AppCompatActivity  implements View.OnClickListener {
    private TextView doctorName, tv_bookingDate;
    private ImageView img_back;
    private Doctor currentDoctor;
    private Booking currentBooking;
    private DialogFragment dateDialogFragment;
    Spinner timeslots;
    DatabaseReference bookingRef;
    private Button submitBtn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dateDialogFragment = new DatePickerFragment();
        tv_bookingDate = findViewById(R.id.bookingDate);
        img_back = findViewById(R.id.img_back);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        tv_bookingDate.setText(day + "/" + month + "/" + year);
        currentBooking = (Booking) getIntent().getSerializableExtra("booking");
        if(currentBooking != null) {
            currentDoctor = currentBooking.getDoctor();
            tv_bookingDate.setText(currentBooking.getDate());
        } else {
            currentDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
        }
        doctorName = findViewById(R.id.doctorName);
        timeslots = findViewById(R.id.timeslots);
        submitBtn = findViewById(R.id.submitBtn);
        img_back.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        doctorName.setText(currentDoctor.getFirstName() + " " + currentDoctor.getLastName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TimeSlot.timeSlots);
        timeslots.setAdapter(adapter);
    }

    private void saveBooking() {
        Booking booking = new Booking();
        booking.setDoctor(currentDoctor);
        booking.setDate(tv_bookingDate.getText().toString());
        booking.setTimeslot(1);
        booking.setNote("Don't forget my booking.");
        booking.setPatientId( FirebaseAuth.getInstance().getCurrentUser().getUid());
        bookingRef= FirebaseDatabase.getInstance().getReference().child("Bookings");
        if(currentBooking != null) {
            booking.setBookingId(currentBooking.getBookingId());
        } else  {
            booking.setBookingId(bookingRef.push().getKey());
        }
        bookingRef.child(booking.getBookingId()).setValue(booking);
        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(BookingActivity.this, "You have successfully booked.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showDatePickerDialog(View v) {

        dateDialogFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.submitBtn:
                saveBooking();
                Intent intent = new Intent(this, MyBookingsActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }
}
