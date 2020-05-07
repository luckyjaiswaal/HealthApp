package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApproveDetailView extends AppCompatActivity {
    private TextView txt_fnameADV;
    private Button   btn_approve;
    DatabaseReference doctorKeyRef;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_detail_view);
        txt_fnameADV = findViewById(R.id.fnameADV);
        btn_approve=findViewById(R.id.approve);

        doctorKeyRef = FirebaseDatabase.getInstance().getReference().child("DoctorApprovalRequests");
        String doctorKey =getIntent().getStringExtra("doctorKey");

        doctorKeyRef.child(doctorKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String FirstName =dataSnapshot.child("FirstName").getValue().toString();

                    txt_fnameADV.setText(FirstName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
