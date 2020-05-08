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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btn_createAccount, btn_SignIn;
    EditText txt_email, txt_password;
    DatabaseReference userTypeRef;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_createAccount=findViewById(R.id.createAccountBtn);
        btn_SignIn=findViewById(R.id.signInBtn);
        txt_email=findViewById(R.id.email);
        txt_password=findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();
        userTypeRef=FirebaseDatabase.getInstance().getReference().child("User");
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));

            }
        });

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               progressDialog=new ProgressDialog(MainActivity.this);
               progressDialog.setMessage("Signing in...");
               progressDialog.show();

                String userEmail =txt_email.getText().toString();
                String userPassword=txt_password.getText().toString();

                if(TextUtils.isEmpty(userEmail)||(TextUtils.isEmpty(userPassword))){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Please enter Email and Password", Toast.LENGTH_LONG).show();
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        String ID = firebaseUser.getUid();
                                        userTypeRef.child(ID).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    userType=dataSnapshot.child("Role").getValue().toString();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));

                                        if(userType.trim().toString().equals("Doctor")){
                                            Toast.makeText(MainActivity.this, "Inword!", Toast.LENGTH_LONG).show();
                                        }
                                        /*else if(userType.equals("Admin")){
                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                            Toast.makeText(MainActivity.this, "IHola"+userType, Toast.LENGTH_LONG).show();

                                        }
                                        else{
                                            startActivity(new Intent(getApplicationContext(),DashboardClean.class));
                                        }*/

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Invalid Email or Password!", Toast.LENGTH_LONG).show();
                                    }


                                }
                            });
                }



            }
        });



            }

}


