package com.wilbrom.mychatapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wilbrom.mychatapp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ItemViewHolder> {

    private ArrayList<String[]> mMessagesList = new ArrayList<>();

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.item_layout_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String message = mMessagesList.get(position)[0];
        String user = mMessagesList.get(position)[1];

        holder.mMessageTextView.setText(message);
        holder.mUserTextView.setText(user);
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    public void setmMessagesList(ArrayList mMessagesList) {
        this.mMessagesList = mMessagesList;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mMessageTextView;
        private TextView mUserTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mMessageTextView = (TextView) itemView.findViewById(R.id.message_textView);
            mUserTextView = (TextView) itemView.findViewById(R.id.user_textView);
        }
    }
}
