package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.adapter.MessageDoctorListAdapter;
import com.example.healthapp.model.Doctor;
import com.example.healthapp.myapplication.Myapplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorMessageList extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUser;
    private RecyclerView patientListView;
    private MessageDoctorListAdapter mMessageDoctorListAdapter;
    private Context mContext;
    List<Doctor> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_message_list_activity);
        mContext = this;
        loaddatList();
    }

    public void loaddatList() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        patientListView = findViewById(R.id.patientListView);
        patientListView.setLayoutManager(new LinearLayoutManager(mContext));
        patientListView.setVisibility(View.VISIBLE);

        mMessageDoctorListAdapter = new MessageDoctorListAdapter(mContext, models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        patientListView.setLayoutManager(mLayoutManager);
        patientListView.setItemAnimator(new DefaultItemAnimator());
        patientListView.setAdapter(mMessageDoctorListAdapter);
        mMessageDoctorListAdapter.setOnItemClickListener(new MessageDoctorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Doctor currentDoctor = models.get(position);
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("doctor", currentDoctor);
                intent.putExtra("chatSystem", 0);
                startActivity(intent);

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("chatRoom");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessageDoctorListAdapter.remvoeAllData();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String Key = snapshot.getKey();
                    String[] separated = Key.split("_");
                    if (separated.length >= 2) {
                        String doctorId = separated[0];
                        final String patiendId = separated[1];

                        if (patiendId.contains(Myapplication.myUserId)) {
                            databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("Doctors").child(doctorId);
                            databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    String Key = dataSnapshot.getKey();
                                    System.out.println("Key:" + models.size() + Key);
                                    Doctor doc = dataSnapshot.getValue(Doctor.class);
                                    doc.setDoctorKey(Key);
                                    mMessageDoctorListAdapter.setSingleData(doc);

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
