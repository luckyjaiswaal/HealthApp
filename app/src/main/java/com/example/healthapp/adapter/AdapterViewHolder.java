package com.example.healthapp.adapter;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * inner class to hold a reference to each item of RecyclerView
 */
public class AdapterViewHolder extends RecyclerView.ViewHolder {

    public AdapterViewHolder(View itemView) {
        super(itemView);
        itemView.setTag(this);
    }

    public TextView getTextView (int resId){
        return (TextView) itemView.findViewById(resId);


    }public RecyclerView getRecycleView(int resId){
        return (RecyclerView) itemView.findViewById(resId);
    }

    public EditText getEditText (int resId){
        return (EditText) itemView.findViewById(resId);
    }

    public ImageView getImageView (int resId){
        return (ImageView) itemView.findViewById(resId);
    }

    public Switch getSwitch (int resId){
        return (Switch) itemView.findViewById(resId);
    }

    public CheckBox getCheckBox (int resId){
        return (CheckBox) itemView.findViewById(resId);
    }

    public VideoView getVideoView (int resId){
        return (VideoView) itemView.findViewById(resId);
    }

    public WebView getWebView (int resId){
        return (WebView) itemView.findViewById(resId);
    }

    public View getView (int resId){
        return (View) itemView.findViewById(resId);
    }

    public Button getButton (int resId) { return (Button) itemView.findViewById(resId);}

    public SeekBar getSeekBar (int resId) {
        return (SeekBar) itemView.findViewById(resId);
    }

    public RelativeLayout getRelativeLayout (int resId) {
        return (RelativeLayout) itemView.findViewById(resId);
    }

    public ConstraintLayout getConstraintLayout (int resId) {
        return (ConstraintLayout) itemView.findViewById(resId);
    }

    public CoordinatorLayout getCoordinatorLayout (int resId) {
        return (CoordinatorLayout) itemView.findViewById(resId);
    }

    public ProgressBar getProgressBar (int resId) {
        return (ProgressBar) itemView.findViewById(resId);
    }

    public LinearLayout getLinearLayout (int resId) {
        return (LinearLayout) itemView.findViewById(resId);
    }
}
