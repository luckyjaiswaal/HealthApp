package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DoctorDashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView layoutMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doctor);
        layoutMessages = (CardView) this.findViewById(R.id.layoutMessages);
        layoutMessages.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutMessages:
                Intent intent = new Intent(DoctorDashboard.this, PatientMessageList.class);
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
