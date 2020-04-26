package com.example.healthapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.R;
import com.example.healthapp.model.ChatModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    List<ChatModel> models;

    public ChatAdapter(Context context, List<ChatModel> models) {
        this.context = context;
        this.models = models;
    }

    public void setData(List<ChatModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel model = models.get(position);
        if(position % 2 == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0)
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set data on views here
        //holder.message.setText(models.get(position).getMessage());
        //holder.time.setText(TimeUtil.getTimeAgo((System.currentTimeMillis() - models.get(position).getTimestamp())/1000));
        //holder.time.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // declare views here
        TextView message;
        TextView time;

        MyViewHolder(View itemView, int viewType) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }
    }
}
