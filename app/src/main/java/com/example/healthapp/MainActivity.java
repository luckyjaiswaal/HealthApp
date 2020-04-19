package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button dashboardTest;
    Button signupBtn;

    EditText textUsername;
    EditText textPassword;
    TextView tvRegister;
    FirebaseAuth mFirebaseAuth;




    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 4000); //2000 is the timeout for the splash screen

        // Button listeners
        dashboardTest = (Button) findViewById(R.id.dashboardTest);
        signupBtn = (Button) findViewById(R.id.signUpBtn);

        dashboardTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainDashboard.class));
            }
        });

        //Login authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textUsername.getText().toString();
                String pwd = textPassword.getText().toString();

                if(email.isEmpty()) {
                    textUsername.setError("Please enter your email.");
                    textUsername.requestFocus();
                }
                else if(pwd.isEmpty()){
                    textPassword.setError("Please enter password");
                    textPassword.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "fields empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty()&& pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!(task.isSuccessful())) {
                                Toast.makeText(MainActivity.this, "Username has already been used!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(MainActivity.this, MainDashboard.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }






}
