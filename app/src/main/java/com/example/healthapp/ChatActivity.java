package com.example.healthapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.adapter.ChatAdapter;
import com.example.healthapp.model.ChatModel;
import com.example.healthapp.util.Utils;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, img_video, img_call, img_search, img_more, img_gallery, img_camera, img_send;
    RecyclerView recyclerView;
    ArrayList<ChatModel> models = new ArrayList<>();
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        img_back = findViewById(R.id.img_back);
        img_video = findViewById(R.id.img_video);
        img_call = findViewById(R.id.img_call);
        img_search = findViewById(R.id.img_search);
        img_more = findViewById(R.id.img_more);
        img_gallery = findViewById(R.id.img_gallery);
        img_camera = findViewById(R.id.img_camera);
        img_send = findViewById(R.id.img_send);

        Utils.changeImageViewColor(this, img_back, R.color.white);
        Utils.changeImageViewColor(this, img_video, R.color.white);
        Utils.changeImageViewColor(this, img_call, R.color.white);
        Utils.changeImageViewColor(this, img_search, R.color.white);
        Utils.changeImageViewColor(this, img_more, R.color.white);

        Utils.changeImageViewColor(this, img_gallery, R.color.colorPrimaryDark);
        Utils.changeImageViewColor(this, img_camera, R.color.colorPrimaryDark);
        Utils.changeImageViewColor(this, img_send, R.color.colorPrimaryDark);

        img_back.setOnClickListener(this);
        img_video.setOnClickListener(this);
        img_call.setOnClickListener(this);
        img_search.setOnClickListener(this);
        img_more.setOnClickListener(this);
        img_gallery.setOnClickListener(this);
        img_camera.setOnClickListener(this);
        img_send.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        fillDummy();
        chatAdapter = new ChatAdapter(this, models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);
    }

    private void fillDummy() {
        models.add(new ChatModel());
        models.add(new ChatModel());
        models.add(new ChatModel());
    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
