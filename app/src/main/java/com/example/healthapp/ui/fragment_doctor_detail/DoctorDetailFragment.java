package com.example.healthapp.ui.fragment_doctor_detail;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthapp.DoctorDetailActivity;
import com.example.healthapp.R;
import com.example.healthapp.models.Doctor;
import com.example.healthapp.ui.doctors.DoctorsFragment;
import com.example.healthapp.ui.doctors.DoctorsViewModel;

public class DoctorDetailFragment extends Fragment {

    private DoctorDetailViewModel mViewModel;

    public static DoctorDetailFragment newInstance() {
        return new DoctorDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.doctor_detail_fragment, container, false);
        TextView tvName = root.findViewById(R.id.doctorName);
        TextView tvIntro = root.findViewById(R.id.introduction);
        tvName.setText(getActivity().getIntent().getStringExtra("doctorName"));
        tvIntro.setText(getActivity().getIntent().getStringExtra("introduction"));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DoctorDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
