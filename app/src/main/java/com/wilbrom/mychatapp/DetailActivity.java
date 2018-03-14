package com.wilbrom.mychatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.wilbrom.mychatapp.adapter.DetailAdapter;
import com.wilbrom.mychatapp.sync.MyMessagingService;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView mMessagesRecyclerView;
    private EditText mMessageEditText;

    private DetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMessageEditText = (EditText) findViewById(R.id.message_editText);
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.detail_recyclerView);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new DetailAdapter();
        mMessagesRecyclerView.setAdapter(mAdapter);
    }

    public void onClickSend(View view) {
        String message = mMessageEditText.getText().toString();

        if (!TextUtils.isEmpty(message)) {
            Intent intent = new Intent(this, MyMessagingService.class);
            intent.setAction(MyMessagingService.ACTION_SEND_MESSAGE);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startService(intent);
        }
    }
}
