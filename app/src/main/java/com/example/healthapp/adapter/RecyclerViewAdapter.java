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
import com.example.healthapp.model.DSModel;
import com.example.healthapp.util.Utils;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<DSModel> models;

    public RecyclerViewAdapter(Context context, List<DSModel> models) {
        this.context = context;
        this.models = models;
    }

    public void setData(List<DSModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_doctor_list, parent, false);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set data on views here
        DSModel model = models.get(position);
        Utils.changeImageViewColor((Activity)context, holder.img_options, R.color.gray);
        if(model.getType() == 1){
            holder.img_avatar.setImageResource(R.drawable.patient);
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
            holder.detail.setTextColor(context.getResources().getColor(R.color.gray));
            holder.title.setText("Patient Name");
        }
        else{
            holder.img_avatar.setImageResource(R.drawable.pharmacy);
            holder.title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.detail.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.title.setText("Pharmacy Name");
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        // declare views here
        ImageView img_avatar, img_options;
        TextView title, detail;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            //initialize views here
            img_avatar = itemView.findViewById(R.id.img_avatar);
            img_options = itemView.findViewById(R.id.img_options);
            title = itemView.findViewById(R.id.txtTitle);
            detail = itemView.findViewById(R.id.txtDetail);
        }
    }
}