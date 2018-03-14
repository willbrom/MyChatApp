package com.wilbrom.mychatapp.sync;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.wilbrom.mychatapp.R;
import com.wilbrom.mychatapp.utils.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

public class MyMessagingService extends Service implements NetworkUtils.OnTransactionCompletionListener {

    private static final String TAG = MyMessagingService.class.getSimpleName();

    // Available actions
    public static final String ACTION_SEND_MESSAGE = "send-message";
    public static final String ACTION_REGISTER = "register";

    // ACTION_SEND_MESSAGE Extras
    public static final String BUNDLE_TYPE_EXTRA = "type-extra";
    public static final String BUNDLE_TITLE_EXTRA = "title-extra";
    public static final String BUNDLE_MESSAGE_EXTRA = "message-extra";
    public static final String BUNDLE_USERID_EXTRA = "userid-extra";
    public static final String BUNDLE_AGENTID_EXTRA = "agentid-extra";

    // ACTION_REGISTER Extras
    public static final String BUNDLE_EMAIL_EXTRA = "email-extra";
    public static final String BUNDLE_TOKEN_EXTRA = "token-extra";

    public MyMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);

        switch (action) {
            case ACTION_SEND_MESSAGE:
                handleActionSendMessage(bundle);
                break;
            case ACTION_REGISTER:
                handleActionRegister(bundle);
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void handleActionRegister(Bundle bundle) {
        String email = bundle.getString(BUNDLE_EMAIL_EXTRA);
        String token = bundle.getString(BUNDLE_TOKEN_EXTRA);

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("fcm_token", token);

        NetworkUtils.setmParams(params);
        NetworkUtils.sendHttpsResponse(this, getString(R.string.url_register), ACTION_REGISTER);
    }

    private void handleActionSendMessage(Bundle bundle) {
        String type = bundle.getString(BUNDLE_TYPE_EXTRA);
        String title = bundle.getString(BUNDLE_TITLE_EXTRA);
        String message = bundle.getString(BUNDLE_MESSAGE_EXTRA);
        String userId = bundle.getString(BUNDLE_USERID_EXTRA);
        String agentId = bundle.getString(BUNDLE_AGENTID_EXTRA);

        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("agent_id", agentId);
        params.put("title", title);
        params.put("body", message);
        params.put("type", type);

        NetworkUtils.setmParams(params);
        NetworkUtils.sendHttpsResponse(this, getString(R.string.url_message), ACTION_SEND_MESSAGE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSuccess(String response, String action) {
        Log.d(TAG, "The response: " + response);

        switch (action) {
            case ACTION_SEND_MESSAGE:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("pref-key", response);
                editor.apply();
        }

        stopSelf();
    }

    @Override
    public void onFailuer(String error, String action) {
        Log.d(TAG, "The error: " + error);
        stopSelf();
    }
}
