package com.wilbrom.mychatapp.utils;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {

    public interface OnTransactionCompletionListener {
        void onSuccess(String response);
        void onFailuer(String error);
    }

    private static OnTransactionCompletionListener mListener;

    private static RequestQueue mRequestQueue;

    public static void sendHttpsResponse(Context context, String url, final String title, final String message) {
        mListener = (OnTransactionCompletionListener) context;

        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (mListener != null)
                    mListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (mListener != null)
                    mListener.onFailuer(error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }
}
