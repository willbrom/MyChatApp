package com.wilbrom.mychatapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.wilbrom.mychatapp.R;
import com.wilbrom.mychatapp.utils.NetworkUtils;

public class MyMessagingService extends Service implements NetworkUtils.OnTransactionCompletionListener {

    private static final String TAG = MyMessagingService.class.getSimpleName();
    public static final String ACTION_SEND_MESSAGE = "send-message";

    public MyMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);

        switch (action) {
            case ACTION_SEND_MESSAGE:
                NetworkUtils.sendHttpsResponse(this, getString(R.string.url), getString(R.string.message_title), message);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSuccess(String response) {
        Log.d(TAG, "The response: " + response);
        stopSelf();
    }

    @Override
    public void onFailuer(String error) {
        Log.d(TAG, "The error: " + error);
        stopSelf();
    }
}
