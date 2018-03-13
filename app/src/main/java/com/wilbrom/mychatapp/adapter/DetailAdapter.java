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

    private Map<String, String> mMessagesList = new LinkedHashMap<>();

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.item_layout_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String message = (new ArrayList<String>(mMessagesList.values())).get(position);
        String user = (new ArrayList<String>(mMessagesList.keySet())).get(position);

        holder.mMessageTextView.setText(message);
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    public void setmMessagesList(Map<String, String> mMessagesList) {
        this.mMessagesList = mMessagesList;
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
