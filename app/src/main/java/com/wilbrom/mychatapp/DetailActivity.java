package com.wilbrom.mychatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wilbrom.mychatapp.adapter.DetailAdapter;
import com.wilbrom.mychatapp.sync.MyFirebaseMessagingService;
import com.wilbrom.mychatapp.sync.MyMessagingService;
import com.wilbrom.mychatapp.utils.JsonUtils;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements MyFirebaseMessagingService.OnMessageReceivedListener {

    public static RecyclerView mMessagesRecyclerView;
    private EditText mMessageEditText;

    private DetailAdapter mAdapter;

    private ArrayList<String[]> mMessagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMessageEditText = (EditText) findViewById(R.id.message_editText);
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.detail_recyclerView);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new DetailAdapter();
        mAdapter.setmMessagesList(mMessagesList);
        mAdapter.notifyDataSetChanged();
        mMessagesRecyclerView.setAdapter(mAdapter);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String data = prefs.getString("pref-key", "no data");
//
//        if (!data.equals("no data")) {
//            mAdapter.setmMessagesList(JsonUtils.parseDetailListJson(this, data));
//        }
        MyFirebaseMessagingService.attachListener(this);
    }

    public void onClickSend(View view) {
        String body = mMessageEditText.getText().toString();
        String type = getString(R.string.type);
        String title = getString(R.string.title);
        String userId = "4";
        String agentId = "3";

        if (!TextUtils.isEmpty(body)) {
            mMessagesList.add(new String[] {body, type, ""});
            mAdapter.notifyItemInserted(mMessagesList.size());
            mMessagesRecyclerView.smoothScrollToPosition(mMessagesList.size());

            Intent intent = new Intent(this, MyMessagingService.class);
            intent.setAction(MyMessagingService.ACTION_SEND_MESSAGE);

            Bundle bundle = new Bundle();
            bundle.putString(MyMessagingService.BUNDLE_TYPE_EXTRA, type);
            bundle.putString(MyMessagingService.BUNDLE_TITLE_EXTRA, title);
            bundle.putString(MyMessagingService.BUNDLE_MESSAGE_EXTRA, body);
            bundle.putString(MyMessagingService.BUNDLE_USERID_EXTRA, userId);
            bundle.putString(MyMessagingService.BUNDLE_AGENTID_EXTRA, agentId);

            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startService(intent);
        }
    }

    @Override
    public void onMessageReceived(String msg) {
        mMessagesList.add(new String[] {msg, msg, msg});
        final String m = msg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemInserted(mMessagesList.size());
                mMessagesRecyclerView.smoothScrollToPosition(mMessagesList.size());
                Toast.makeText(DetailActivity.this, m, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
