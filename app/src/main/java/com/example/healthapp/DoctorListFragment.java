package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthapp.adapter.DoctorListAdapter;
import com.example.healthapp.model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorListFragment extends Fragment {

    private DoctorListViewModel mViewModel;
    List<Doctor> models = new ArrayList<>();
    private RecyclerView doctorListView;
    private DoctorListAdapter mAdapter;
    DatabaseReference databaseReference;

    public static DoctorListFragment newInstance() {
        return new DoctorListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_list_fragment, container, false);
        doctorListView = rootView.findViewById(R.id.doctorListView);
        doctorListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        doctorListView.setVisibility(View.VISIBLE);
        setAdapterList();
        return rootView;
    }

    private void setAdapterList() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Doctor doc = snapshot.getValue(Doctor.class);
                    doc.setDoctorId(snapshot.getKey());
                    System.out.println("Modles:" + models.size() + doc.getFirstName());
                    models.add(doc);
                    mAdapter = new DoctorListAdapter(getActivity(), models);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    doctorListView.setLayoutManager(mLayoutManager);
                    doctorListView.setItemAnimator(new DefaultItemAnimator());
                    doctorListView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new DoctorListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Doctor selectedItem = models.get(position);
                            Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
                            intent.putExtra("doctor", selectedItem);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment, fragment.toString());
            fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        }
    }

}
