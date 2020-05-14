package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthapp.adapter.BookingListAdapter;
import com.example.healthapp.adapter.DoctorListAdapter;
import com.example.healthapp.model.Booking;
import com.example.healthapp.model.Doctor;
import com.example.healthapp.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private RecyclerView bookingList;
    private BookingListAdapter mAdapter;
    DatabaseReference bookingRef;
    DatabaseReference userTypeRef;
    List<Booking> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        img_back = findViewById(R.id.img_back);
        bookingList = findViewById(R.id.bookingList);
        bookingList.setVisibility(View.VISIBLE);
        setAdapterList();
        img_back.setOnClickListener(this);
    }

    private void setAdapterList() {
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Bookings");
        userTypeRef=FirebaseDatabase.getInstance().getReference().child("User");
        userTypeRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query;
                if(dataSnapshot.exists()){
                    String userType=dataSnapshot.child("Role").getValue().toString();
                    if(userType.equals("Doctor")){
                        query = bookingRef.orderByChild("patientId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                    else {
                        query = bookingRef.orderByChild("patientId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                    query.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            models.add(dataSnapshot.getValue(Booking.class));
                            mAdapter = new BookingListAdapter(getActivity(), models);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            bookingList.setLayoutManager(mLayoutManager);
                            bookingList.setItemAnimator(new DefaultItemAnimator());
                            bookingList.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(new BookingListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, String action) {
                                    Booking selectedItem = models.get(position);
                                    if(action.equals("edit")) {
                                        Intent intent = new Intent(getActivity(), BookingActivity.class);
                                        intent.putExtra("booking", selectedItem);
                                        finish();
                                        startActivity(intent);
                                    } else {
                                        bookingRef.child(selectedItem.getBookingId()).removeValue();
                                        Toast.makeText(MyBookingsActivity.this, "You have successfully cancelled.", Toast.LENGTH_LONG).show();
                                        models.clear();
                                        setAdapterList();
                                    }

                                }
                            });
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private AppCompatActivity getActivity(){
        return this;
    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
