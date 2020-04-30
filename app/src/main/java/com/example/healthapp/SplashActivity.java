package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.splash_activity);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
        imgLogo.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //The below code to replaced by correct activity call-LJ
              //  startActivity(new Intent(SplashActivity.this, ChooseLoginActivity.class));
                finish();
            }
        }, 3*1000);
    }
}
