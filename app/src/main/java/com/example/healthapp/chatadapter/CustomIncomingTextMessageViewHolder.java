package com.example.healthapp.chatadapter;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.example.healthapp.R;
import com.example.healthapp.chatmodel.GetTimeCovertAgo;
import com.example.healthapp.chatmodel.Message;
import com.stfalcon.chatkit.messages.MessageHolders;


public class CustomIncomingTextMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<Message> {

    private View onlineIndicator;
    public CustomIncomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        onlineIndicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        String textTime = GetTimeCovertAgo.getNewsFeeTimeAgo(message.getCreatedAt().getTime());
        time.setText(textTime);
        text.setText(Html.fromHtml(message.getText()));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
