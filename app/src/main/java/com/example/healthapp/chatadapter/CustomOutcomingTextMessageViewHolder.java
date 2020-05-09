package com.example.healthapp.chatadapter;

import android.view.View;

import com.example.healthapp.chatmodel.GetTimeCovertAgo;
import com.example.healthapp.chatmodel.Message;
import com.stfalcon.chatkit.messages.MessageHolders;


public class CustomOutcomingTextMessageViewHolder
        extends MessageHolders.OutcomingTextMessageViewHolder<Message> {
    public CustomOutcomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        String textDuratin = GetTimeCovertAgo.getNewsFeeTimeAgo(message.getCreatedAt().getTime());
        time.setText(textDuratin);

    }
}
