package com.wilbrom.mychatapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wilbrom.mychatapp.adapter.MainAdapter;
import com.wilbrom.mychatapp.utils.FakeData;
import com.wilbrom.mychatapp.utils.JsonUtils;
import com.wilbrom.mychatapp.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String DETAIL_EXTRA = "detail-extra";
    public static final int LOADER_ID = 11;

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    private List fakeData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        fakeData = FakeData.getFakeData();
        mAdapter.setmItemList(fakeData);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null)
            loaderManager.initLoader(LOADER_ID, null, this);
        else
            loaderManager.restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "Position in adapter: " + position);
        Intent intent = new Intent(this , DetailActivity.class);
        intent.putExtra(DETAIL_EXTRA, position);
        startActivity(intent);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader(this) {

            String response;

            @Override
            protected void onStartLoading() {
                if (response != null)
                    deliverResult(response);
                else
                    forceLoad();
            }

            @Override
            public Object loadInBackground() {
                try {
                    URL url = new URL(getString(R.string.url_list));
                    return NetworkUtils.getHttpsResponse(url);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Object data) {
                response = (String) data;
                Log.d(TAG, "Response: "+ response);
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        String result = (String) data;
        Log.d(TAG, "Result: " + result);
        if (data != null) {
            List list = JsonUtils.parseMainListJson(this, result);
            Log.d(TAG, "List Size: " + list.size());
            mAdapter.setmItemList(list);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
