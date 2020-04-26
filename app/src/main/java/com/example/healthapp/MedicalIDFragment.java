package com.example.healthapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthapp.util.Utils;

public class MedicalIDFragment extends Fragment {
    ImageView profile;

    public MedicalIDFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_medical_id, container, false);
        profile = rootView.findViewById(R.id.img_avatar);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.changeImageViewColor(getActivity(), profile, R.color.black);
    }
}
