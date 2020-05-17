package com.example.healthapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.R;
import com.example.healthapp.model.Booking;
import com.example.healthapp.model.TimeSlot;
import com.example.healthapp.util.Utils;

import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.MyViewHolder> {
    Context context;
    List<Booking> models;
    private OnItemClickListener mListener;

    public BookingListAdapter(Context context, List<Booking> models) {
        this.context = context;
        this.models = models;
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<Booking> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_booking_list, parent, false);
        return new MyViewHolder(view, viewType, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set data on views here
        Booking model = models.get(position);
        holder.title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.detail.setTextColor(context.getResources().getColor(R.color.gray));
        holder.title.setText(model.getDate() + " " + TimeSlot.timeSlots.get(model.getTimeslot()));
        holder.detail.setText("Doctor: " + model.getDoctor().getFirstName() + " " + model.getDoctor().getLastName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String action);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // declare views here
        TextView title, detail;
        Button cancelBtn, editBtn;

        public MyViewHolder(View itemView, int viewType, final OnItemClickListener listener) {
            super(itemView);
            //initialize views here
            title = itemView.findViewById(R.id.txtTitle);
            detail = itemView.findViewById(R.id.txtDescription);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
            editBtn = itemView.findViewById(R.id.editBtn);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, "cancel");
                        }
                    }
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, "edit");
                        }
                    }
                }
            });
        }
    }
}