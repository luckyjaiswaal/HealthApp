package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {
    private Button btn_viewRequests,btn_Logout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        btn_viewRequests=findViewById(R.id.viewRequests);
        btn_Logout=findViewById(R.id.logout);
        firebaseAuth=FirebaseAuth.getInstance();

        btn_viewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ApprovalList.class));
            }
        });

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Logout.setText("Logging out...");
                btn_Logout.setEnabled(false);
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}
