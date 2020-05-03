package com.example.healthapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    TextView tv_bookingTime;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        tv_bookingTime = getActivity().findViewById(R.id.bookingDate);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        month++;
        tv_bookingTime.setText(day + "/" + month + "/" + year);
    }
}