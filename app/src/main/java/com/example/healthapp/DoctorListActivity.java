package com.example.healthapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DoctorListFragment.newInstance())
                    .commitNow();
        }
    }
}
