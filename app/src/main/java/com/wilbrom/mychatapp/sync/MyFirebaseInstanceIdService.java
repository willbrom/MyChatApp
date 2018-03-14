package com.wilbrom.mychatapp.sync;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.wilbrom.mychatapp.R;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "From service:" + token);

        Intent intent = new Intent(this, MyMessagingService.class);
        intent.setAction(MyMessagingService.ACTION_REGISTER);

        Bundle bundle = new Bundle();
        bundle.putString(MyMessagingService.BUNDLE_TOKEN_EXTRA, token);
        bundle.putString(MyMessagingService.BUNDLE_EMAIL_EXTRA, getString(R.string.email));

        intent.putExtra(Intent.EXTRA_TEXT, bundle);
        startService(intent);
    }
}
