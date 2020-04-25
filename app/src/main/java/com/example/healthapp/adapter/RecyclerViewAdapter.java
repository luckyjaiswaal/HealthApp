package com.example.healthapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.model.SafeJSONArray;
import com.example.healthapp.model.SafeJSONObject;


public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SafeJSONArray itemsData;
    protected Context ctx;
    protected View.OnClickListener mListener;
    protected int layoutResource;

    public RecyclerViewAdapter(Context context, int layoutResource2){
        this.ctx = context;
        this.layoutResource = layoutResource2;
    }

    public void setOnClickListener(View.OnClickListener mItemClickListener) {
        this.mListener = mItemClickListener;
    }

    public void setItemsData (SafeJSONArray itemsData1){
//        if(itemsData1 != null) {
            this.itemsData =new SafeJSONArray(itemsData1.toString());
//            Log.e("itemsData","itemsData = " + itemsData);
            super.notifyDataSetChanged();
//        }
    }

    public void notifyDataSetChanged(SafeJSONArray itemsData2) {
        // TODO Auto-generated method stub
        this.itemsData = new SafeJSONArray(itemsData2.toString());
        super.notifyDataSetChanged();
    }

    public SafeJSONObject getItem(int position) {
        // TODO Auto-generated method stub
        return itemsData.getJSONObject(position);
    }

    public SafeJSONArray getallItems (){
        return itemsData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                    layoutResource, parent, false);
            // create ViewHolder
        return new AdapterViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (itemsData == null)
            return 0;

        int length = itemsData.length();
        //Logger.debug("length: "+length);
        return length;
    }

    public RecyclerView.ViewHolder getViewHolder(View onClickView, int itemResId) {
        RecyclerView.ViewHolder viewHolder;
        int parentResId = onClickView.getId();
        if (parentResId == itemResId){
            viewHolder = (RecyclerView.ViewHolder) onClickView.getTag();
        }
        else {
            View parent = ((View) onClickView.getParent());
            parentResId = parent.getId();
            while (parentResId != itemResId){

                //todo Break infinite loop because Your parent View has reached to RecyclerView;
                if (parent instanceof RecyclerView){
                    return null;
                }

                parent = ((View) parent.getParent());
                parentResId = parent.getId();
            }
            viewHolder = (RecyclerView.ViewHolder) parent.getTag();
        }
        return viewHolder;
    }
}
