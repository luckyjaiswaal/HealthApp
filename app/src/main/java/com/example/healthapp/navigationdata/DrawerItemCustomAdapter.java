package com.example.healthapp.navigationdata;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.healthapp.R;
import com.example.healthapp.util.Utils;

public class DrawerItemCustomAdapter extends ArrayAdapter<DataModel> {
    Context mContext;
    int layoutResourceId;
    DataModel data[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, DataModel[] data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        ConstraintLayout layoutHeader = (ConstraintLayout) listItem.findViewById(R.id.layoutHeader);
        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        if (position == 0){
            layoutHeader.setVisibility(View.VISIBLE);
            ImageView imgLogo = (ImageView) listItem.findViewById(R.id.img_logo);
            if(imgLogo != null)
                Utils.changeImageViewColor((Activity) mContext, imgLogo, R.color.white);
            imageViewIcon.setVisibility(View.GONE);
            textViewName.setVisibility(View.GONE);
        }
        else {
            layoutHeader.setVisibility(View.GONE);
            imageViewIcon.setVisibility(View.VISIBLE);
            textViewName.setVisibility(View.VISIBLE);
            DataModel folder = data[position];
            imageViewIcon.setImageResource(folder.icon);
            if(imageViewIcon != null)
                Utils.changeImageViewColor((Activity) mContext, imageViewIcon, R.color.white);
            textViewName.setText(folder.name);
        }

        return listItem;
    }
}

