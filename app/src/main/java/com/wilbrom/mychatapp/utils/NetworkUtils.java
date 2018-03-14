package com.wilbrom.mychatapp.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    public interface OnTransactionCompletionListener {
        void onSuccess(String response, String action);
        void onFailuer(String error, String action);
    }

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static OnTransactionCompletionListener mListener;

    private static RequestQueue mRequestQueue;
    private static Map mParams;

    public static void sendHttpsResponse(Context context, String url, final String action) {
        mListener = (OnTransactionCompletionListener) context;

        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (mListener != null)
                    mListener.onSuccess(response, action);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (mListener != null)
                    mListener.onFailuer(error.toString(), action);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                if (mParams != null)
                    return mParams;
                else
                    throw new UnsupportedOperationException("This should not occur!");
            }
        };

        mRequestQueue.add(stringRequest);
    }

    public static String getHttpsResponse(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = con.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            if (sc.hasNext()) {
                String s = sc.next();
                Log.d(TAG, "This is res: " + s);
                return s;
            }
        } finally {
            con.disconnect();
        }
        return null;
    }

    public static Map getmParams() {
        return mParams;
    }

    public static void setmParams(Map mParams) {
        NetworkUtils.mParams = mParams;
    }
}
