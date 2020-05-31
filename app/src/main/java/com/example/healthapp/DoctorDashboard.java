package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DoctorDashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView layoutMessages;
    private CardView patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doctor);
        layoutMessages = (CardView) this.findViewById(R.id.layoutMessages);
        patientList = (CardView) this.findViewById(R.id.patientList);
        layoutMessages.setOnClickListener(this);
        patientList.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutMessages:
                Intent intent = new Intent(DoctorDashboard.this, PatientMessageList.class);
                startActivity(intent);
                break;
            case R.id.patientList:
                Intent patientListIntent = new Intent(DoctorDashboard.this, MyBookingsActivity.class);
                startActivity(patientListIntent);
                break;
            default:
                break;
        }
    }
}
