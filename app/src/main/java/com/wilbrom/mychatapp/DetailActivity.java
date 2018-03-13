package com.wilbrom.mychatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wilbrom.mychatapp.adapter.DetailAdapter;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView detailRecyclerView;
    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailRecyclerView = (RecyclerView) findViewById(R.id.detail_recyclerView);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new DetailAdapter();
        detailRecyclerView.setAdapter(adapter);
    }
}
