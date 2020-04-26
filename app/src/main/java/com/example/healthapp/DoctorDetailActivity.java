package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthapp.ui.fragment_doctor_detail.DoctorDetailFragment;

public class DoctorDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_detail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DoctorDetailFragment.newInstance())
                    .commitNow();
        }


    }
}
