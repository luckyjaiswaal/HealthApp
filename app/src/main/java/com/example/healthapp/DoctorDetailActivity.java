package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthapp.model.Doctor;
import com.example.healthapp.util.Utils;

public class DoctorDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, moreInformation;
    private TextView doctorName, introduction;
    private Doctor currentDoctor;
    private Button bookBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        moreInformation = findViewById(R.id.moreInformation);
        img_back = findViewById(R.id.img_back);
        doctorName = findViewById(R.id.doctorName);
        introduction = findViewById(R.id.introduction);
        bookBtn = findViewById(R.id.bookBtn);
        currentDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
        doctorName.setText(currentDoctor.getFirstName() + " " + currentDoctor.getLastName());
        introduction.setText(currentDoctor.getIntroduction());
        img_back.setOnClickListener(this);
        bookBtn.setOnClickListener(this);
        moreInformation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.moreInformation:
                popupMenu();
                break;

        }
    }

    public void popupMenu() {
        PopupMenu popup = new PopupMenu(DoctorDetailActivity.this, moreInformation);
        popup.getMenuInflater().inflate(R.menu.menu_doctors, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_booknow) {
                    Intent intent = new Intent(DoctorDetailActivity.this, BookingActivity.class);
                    intent.putExtra("doctor", currentDoctor);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.action_chat) {
                    Intent intent = new Intent(DoctorDetailActivity.this, ChatActivity.class);
                    intent.putExtra("doctor", currentDoctor);
                    intent.putExtra("chatSystem", 0);
                    startActivity(intent);
                    return true;
                }

                return true;
            }
        });
        popup.show();
    }

}
