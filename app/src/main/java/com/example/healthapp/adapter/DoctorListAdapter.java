package com.example.healthapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.R;
import com.example.healthapp.model.Doctor;
import com.example.healthapp.util.Utils;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {
    Context context;
    List<Doctor> models;
    private OnItemClickListener mListener;

    public DoctorListAdapter(Context context, List<Doctor> models) {
        this.context = context;
        this.models = models;
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<Doctor> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_doctor_list, parent, false);
        return new MyViewHolder(view, viewType, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set data on views here
        Doctor model = models.get(position);
        Utils.changeImageViewColor((Activity) context, holder.img_options, R.color.gray);
        holder.img_avatar.setImageResource(R.drawable.patient);
        holder.title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.detail.setTextColor(context.getResources().getColor(R.color.gray));
        holder.title.setText(model.getFirstName() + " " + model.getLastName());
        holder.detail.setText(model.getDepartment());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // declare views here
        ImageView img_avatar, img_options;
        TextView title, detail;

        public MyViewHolder(View itemView, int viewType, final OnItemClickListener listener) {
            super(itemView);
            //initialize views here
            img_avatar = itemView.findViewById(R.id.img_avatar);
            img_options = itemView.findViewById(R.id.img_options);
            title = itemView.findViewById(R.id.txtTitle);
            detail = itemView.findViewById(R.id.txtDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}