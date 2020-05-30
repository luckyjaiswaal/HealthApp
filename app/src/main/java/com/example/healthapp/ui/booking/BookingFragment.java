package com.example.healthapp.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthapp.R;

import java.util.Arrays;
import java.util.List;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private List<String> nameList = Arrays.asList("Jack", "Tom", "Molly", "May");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        // Create a List from String Array elements
        // Create an ArrayAdapter from List
        final ArrayAdapter<String> myListAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.booking_item,
                R.id.patientName,
                nameList);

        ListView listView = (ListView) root.findViewById(R.id.bookingList);
        listView.setAdapter(myListAdapter);
        return root;
    }
}
