package com.wilbrom.mychatapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wilbrom.mychatapp.DetailActivity;
import com.wilbrom.mychatapp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ItemViewHolder> {

    private static final String TAG = DetailAdapter.class.getSimpleName();
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
        String date = mMessagesList.get(position)[2];

        holder.mMessageTextView.setText(message);
        holder.mUserTextView.setText(user);
        holder.mDateTextView.setText(date);
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    public void setmMessagesList(ArrayList mMessagesList) {
        this.mMessagesList = mMessagesList;
    }

    public void insertNewItem(String[] newItem) {
        Log.d(TAG, "Item added: " + this.mMessagesList.add(newItem));

//        DetailActivity.mMessagesRecyclerView.smoothScrollToPosition(mMessagesList.size());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mMessageTextView;
        private TextView mUserTextView;
        private TextView mDateTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mMessageTextView = (TextView) itemView.findViewById(R.id.message_textView);
            mUserTextView = (TextView) itemView.findViewById(R.id.user_textView);
            mDateTextView = (TextView) itemView.findViewById(R.id.date_textView);
        }
    }
}
