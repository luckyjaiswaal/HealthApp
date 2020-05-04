package com.example.healthapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthapp.model.Doctor;
import com.example.healthapp.model.TimeSlot;
import com.example.healthapp.util.Utils;

import java.util.stream.Collectors;

public class DoctorDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView doctorName, introduction;
    private Doctor currentDoctor;
    private Button bookBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        img_back = findViewById(R.id.img_back);
        doctorName = findViewById(R.id.doctorName);
        introduction = findViewById(R.id.introduction);
        bookBtn = findViewById(R.id.bookBtn);
        currentDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
        doctorName.setText(currentDoctor.getFirstName() + " " + currentDoctor.getLastName());
        introduction.setText(currentDoctor.getIntroduction());
        img_back.setOnClickListener(this);
        bookBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.bookBtn:
                Intent intent = new Intent(this, BookingActivity.class);
                intent.putExtra("doctor", currentDoctor);
                startActivity(intent);
                break;
        }
    }
}
