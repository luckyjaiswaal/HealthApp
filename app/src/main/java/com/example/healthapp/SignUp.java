package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText txt_firstName, txt_lastNAme,txt_email,txt_password,txt_confirmPassword;
    Button btn_signUp;
    RadioButton radioMale,radioFemale;
    String role = "Patient";
    String gender = "";
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_firstName = findViewById(R.id.firstName);
        txt_lastNAme = findViewById(R.id.lastName);
        txt_email= findViewById(R.id.email);
        txt_password = findViewById(R.id.password);
        txt_confirmPassword = findViewById(R.id.confirmPassword);
        btn_signUp = findViewById(R.id.signUpBtn);
        radioMale = findViewById(R.id.rdMaleBtn);
        radioFemale = findViewById(R.id.rdFemaleBtn);
        firebaseAuth=FirebaseAuth.getInstance();


        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 final String firstName = txt_firstName.getText().toString();
                 final String lastName = txt_lastNAme.getText().toString();
                 final String email = txt_email.getText().toString().trim();
                 String password = txt_password.getText().toString().trim();
                 String confirmPassword = txt_confirmPassword.getText().toString();

                if(radioMale.isChecked()){
                    gender = "Male";
                }

                if(radioFemale.isChecked()){
                    gender = "Female";
                }


                // Here user add code...
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

                            HashMap<String,Object> map = new HashMap<>();
                            map.put("First Name", firstName);
                            map.put("Last Name",lastName);
                            map.put("Gender",gender);
                            map.put("Role",role);
                            map.put("Email",email);

                            databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

            }
        });
    }
}


