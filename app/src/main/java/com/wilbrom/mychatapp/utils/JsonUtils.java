package com.wilbrom.mychatapp.utils;

import android.content.Context;
import android.util.Log;

import com.wilbrom.mychatapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static List parseMainListJson(Context context, String rawJson) {
        List resultList = new ArrayList();

        try {
            JSONObject rootObj = new JSONObject(rawJson);
            JSONArray resultArray = rootObj.getJSONArray("result");
            Log.d(TAG, "ResultArray length: " + resultArray.length());

            for (int i = 0; resultArray.length() > i; i++ ) {
                JSONObject obj = resultArray.getJSONObject(i);
                int id = (int) obj.get(context.getString(R.string.list_id));
                Log.d(TAG, "ID: " + id);
                resultList.add(String.valueOf(id));
            }

            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> parseDetailListJson(Context context, String rawJson) {
        ArrayList<String[]> dataList = new ArrayList<>();

        try {
            JSONObject rootObj = new JSONObject(rawJson);
            JSONArray dataArray = rootObj.getJSONArray("data");
            Log.d(TAG, "dataArray length: " + dataArray.length());

            for (int i = 0; i < dataArray.length(); i++) {
                String msg = dataArray.getJSONObject(i).getString("message");
                String type = dataArray.getJSONObject(i).getString("type");
                String date = dataArray.getJSONObject(i).getString("created_at");

                dataList.add(new String[]{msg, type, date});
            }

            return dataList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
