package com.example.healthapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.healthapp.model.Doctor;
import com.example.healthapp.model.TimeSlot;
import com.example.healthapp.util.Utils;

public class BookingActivity extends AppCompatActivity  implements View.OnClickListener {
    private TextView doctorName, tv_bookingTime;
    private ImageView img_back;
    private Doctor currentDoctor;
    private DialogFragment dateDialogFragment;
    Spinner timeslots;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dateDialogFragment = new DatePickerFragment();
        tv_bookingTime = findViewById(R.id.bookingDate);
        img_back = findViewById(R.id.img_back);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        tv_bookingTime.setText(day + "/" + month + "/" + year);
        currentDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
        doctorName = findViewById(R.id.doctorName);
        timeslots = findViewById(R.id.timeslots);
        img_back.setOnClickListener(this);
        doctorName.setText(currentDoctor.getFirstName() + " " + currentDoctor.getLastName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TimeSlot.timeSlots);
        timeslots.setAdapter(adapter);
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
        }
    }
}
