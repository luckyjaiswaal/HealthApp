package com.example.healthapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView txt_name;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name=itemView.findViewById(R.id.fname);
    }
}
