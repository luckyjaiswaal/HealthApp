package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ApproveDetailView extends AppCompatActivity {
    private TextView txt_nameADV;
    private Button   btn_approve;
    private Button   btn_return;
    private Button   btn_reject;
    DatabaseReference doctorKeyRef;
    DatabaseReference doctorRef;
    DatabaseReference userRef;
    DatabaseReference doctorApprovalRequestsRef;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_detail_view);
        txt_nameADV = findViewById(R.id.nameADV);
        btn_approve=findViewById(R.id.approve);
        btn_return=findViewById(R.id.returnBack);
        btn_reject=findViewById(R.id.reject);
        doctorApprovalRequestsRef=FirebaseDatabase.getInstance().getReference().child("DoctorApprovalRequests");
        doctorRef=FirebaseDatabase.getInstance().getReference().child("Doctors");
        doctorKeyRef = FirebaseDatabase.getInstance().getReference().child("User");
        final String doctorKey =getIntent().getStringExtra("doctorKey");

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_approve.setText("Approving...");
                userRef=FirebaseDatabase.getInstance().getReference().child("User");
                userRef.child(doctorKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String doc_firstName=dataSnapshot.child("First Name").getValue().toString();
                            String doc_lastName=dataSnapshot.child("Last Name").getValue().toString();
                            String doc_gender=dataSnapshot.child("Gender").getValue().toString();
                            String doc_email=dataSnapshot.child("Email").getValue().toString();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("FirstName", doc_firstName);
                            map.put("LastName", doc_lastName);
                            map.put("Gender", doc_gender);
                            map.put("Email", doc_email);
                            map.put("Department","Department");
                            map.put("Introduction","Hello, Iam a Doctor");

                            doctorRef.child(doctorKey).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        btn_approve.setText("Approved");
                                        btn_approve.setEnabled(false);
                                        btn_reject.setEnabled(false);
                                        doctorApprovalRequestsRef.child(doctorKey).removeValue();
                                        userRef.child(doctorKey).child("Role").setValue("Doctor");
                                        btn_reject.setEnabled(false);
                                    }
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorApprovalRequestsRef.child(doctorKey).removeValue();
                btn_reject.setText("Request Rejected");
                btn_reject.setEnabled(false);
                btn_approve.setEnabled(false);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ApprovalList.class));
            }
        });

        doctorKeyRef.child(doctorKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String FirstName =dataSnapshot.child("First Name").getValue().toString();
                    String LastName =dataSnapshot.child("Last Name").getValue().toString();
                    txt_nameADV.setText(FirstName+ " " + LastName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
