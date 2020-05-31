package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class DoctorDashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView layoutMessages;
    private CardView patientList;
    private Button logoutBtn;
    //private FirebaseAuth firebaseAuth;
    private ImageView doctor_logout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doctor);
        layoutMessages = (CardView) this.findViewById(R.id.layoutMessages);
        patientList = (CardView) this.findViewById(R.id.patientList);
        layoutMessages.setOnClickListener(this);
        patientList.setOnClickListener(this);
        doctor_logout = findViewById(R.id.dashboardImg);
        //firebaseAuth=FirebaseAuth.getInstance();

        //logoutBtn = findViewById(R.id.dashboardImg);

        doctor_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

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
