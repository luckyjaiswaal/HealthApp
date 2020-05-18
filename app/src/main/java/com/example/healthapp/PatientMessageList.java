package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.adapter.MessageUserListAdapter;
import com.example.healthapp.model.Patient;
import com.example.healthapp.myapplication.Myapplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientMessageList extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUser;
    private RecyclerView patientListView;
    private MessageUserListAdapter mMessageUserListAdapter;
    private Context mContext;
    List<Patient> models = new ArrayList<>();

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

        mMessageUserListAdapter = new MessageUserListAdapter(mContext, models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        patientListView.setLayoutManager(mLayoutManager);
        patientListView.setItemAnimator(new DefaultItemAnimator());
        patientListView.setAdapter(mMessageUserListAdapter);
        mMessageUserListAdapter.setOnItemClickListener(new MessageUserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Patient selectedItem = models.get(position);
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("patient", selectedItem);
                intent.putExtra("chatSystem", 1);
                startActivity(intent);
                System.out.println(models.get(position).getFirstName());
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("chatRoom");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String Key = snapshot.getKey();
                    String[] separated = Key.split("_");
                    if (separated.length >= 2) {
                        String doctorId = separated[0];
                        final String patiendId = separated[1];

                        if (doctorId.contains(Myapplication.myUserId)) {
                            databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("User").child(patiendId);
                            databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String Email = dataSnapshot.child("Email").getValue(String.class);
                                    String FirstName = dataSnapshot.child("First Name").getValue(String.class);
                                    String LastName = dataSnapshot.child("Last Name").getValue(String.class);
                                    Patient mPatient = new Patient();
                                    mPatient.setEmail(Email);
                                    mPatient.setFirstName(FirstName);
                                    mPatient.setLastName(LastName);
                                    mPatient.setPatientKey(patiendId);
                                    mMessageUserListAdapter.setSingleData(mPatient);
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
