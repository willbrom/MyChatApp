package com.wilbrom.mychatapp.sync;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wilbrom.mychatapp.adapter.DetailAdapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public interface OnMessageReceivedListener {
        void onMessageReceived(String msg);
    }

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private static OnMessageReceivedListener mListener;

    public static void attachListener(Context context) {
        mListener = (OnMessageReceivedListener) context;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String body = remoteMessage.getNotification().getBody();
//        new DetailAdapter().insertNewItem(new String[]{body, body, body});
        Log.d(TAG, "Notification body is: " + body);

        if (mListener != null)
            mListener.onMessageReceived(body);
    }
}
