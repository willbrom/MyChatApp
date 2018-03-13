package com.wilbrom.mychatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wilbrom.mychatapp.adapter.MainAdapter;
import com.wilbrom.mychatapp.utils.FakeData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    public static final String DETAIL_EXTRA = "detail-extra";

    private RecyclerView mainRecyclerView;
    private MainAdapter mainAdapter;

    private List fakeData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mainAdapter = new MainAdapter(this);
        mainRecyclerView.setAdapter(mainAdapter);

        fakeData = FakeData.getFakeData();
        mainAdapter.setmItemList(fakeData);

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this , DetailActivity.class);
        intent.putExtra(DETAIL_EXTRA, position);
        startActivity(intent);
    }
}
