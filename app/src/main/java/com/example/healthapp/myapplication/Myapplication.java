package com.example.healthapp.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.google.firebase.FirebaseApp;


public class Myapplication extends Application {
    private static Myapplication mInstance;
    private static Context mContext;
    public static int selection = 0;
    public static String NEW_MESSAGE_ACTION="new_message";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mInstance = this;
        mContext = this;

    }


    public static Context getContext() {
        return mContext;
    }

    public static Myapplication getInstance() {
        return mInstance;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
