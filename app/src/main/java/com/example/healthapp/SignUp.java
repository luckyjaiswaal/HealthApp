package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText txt_firstName, txt_lastNAme,txt_email,txt_password,txt_confirmPassword;
    Button btn_signUp,btn_login;
    RadioButton radioMale,radioFemale;
    String role = "Patient";
    String gender = "";
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

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
        btn_login=findViewById(R.id.loginBtn);
        radioMale = findViewById(R.id.rdMaleBtn);
        radioFemale = findViewById(R.id.rdFemaleBtn);
        firebaseAuth=FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Signing up...");
                final String firstName = txt_firstName.getText().toString();
                final String lastName = txt_lastNAme.getText().toString();
                final String email = txt_email.getText().toString().trim();
                String password = txt_password.getText().toString().trim();
                String confirmPassword = txt_confirmPassword.getText().toString();

                if (radioMale.isChecked()) {
                    gender = "Male";
                }

                if (radioFemale.isChecked()) {
                    gender = "Female";
                }

                if (TextUtils.isEmpty(firstName) || (TextUtils.isEmpty(lastName)) || (TextUtils.isEmpty(email)) || (TextUtils.isEmpty(password)) || (TextUtils.isEmpty(confirmPassword))) {
                    Toast.makeText(SignUp.this, "Please fill in all the fields.", Toast.LENGTH_LONG).show();
                } else if (!(password.equals(confirmPassword))) {
                    Toast.makeText(SignUp.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                } else if (password.length() < 8) {
                    Toast.makeText(SignUp.this, "Password must be eight characters long.", Toast.LENGTH_LONG).show();
                } else if (!(gender == "Male" || gender == "Female")) {
                    Toast.makeText(SignUp.this, "Please select gender.", Toast.LENGTH_LONG).show();
                } else if (email.length() < 5) {
                    Toast.makeText(SignUp.this, "Please enter a valid email.", Toast.LENGTH_LONG).show();
                 }
                else {
                     //progressDialog.show();
                    // Here user add code...
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "Account registered. Please verify your email to login.", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(SignUp.this, "User Already Registered.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String userId = firebaseUser.getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("First Name", firstName);
                                map.put("Last Name", lastName);
                                map.put("Gender", gender);
                                map.put("Role", role);
                                map.put("Email", email);

                                databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        //Toast.makeText(SignUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                    }
                                });
                            }else {

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SignUp.this, "User with this email already exists.", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                    });
                }

            }
        });
    }
}


