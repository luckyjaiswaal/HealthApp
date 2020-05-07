package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApprovalList extends AppCompatActivity {
    DatabaseReference ref;
    private FirebaseRecyclerOptions<DoctorApprovalList> options;
    private FirebaseRecyclerAdapter<DoctorApprovalList,MyViewHolder> adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_list);

        recyclerView=findViewById(R.id.recyclerView_AL);
        ref=FirebaseDatabase.getInstance().getReference().child("DoctorApprovalRequests");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        options=new FirebaseRecyclerOptions.Builder<DoctorApprovalList>().setQuery(ref,DoctorApprovalList.class).build();
        adapter=new FirebaseRecyclerAdapter<DoctorApprovalList, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull DoctorApprovalList model) {
                holder.txt_name.setText(model.getFirstName());

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ApprovalList.this,ApproveDetailView.class);
                        intent.putExtra("doctorKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }



}



