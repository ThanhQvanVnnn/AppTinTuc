package com.example.apptintuc;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.apptintuc.Adapter.LuuTinAdapter;
import com.example.apptintuc.Adapter.NewsAdapter;
import com.example.apptintuc.GetDataBase.TinTucViewModel;
import com.example.apptintuc.Object.TinTuc;

import java.util.ArrayList;
import java.util.List;

public class LuuTin extends AppCompatActivity {

    private TinTucViewModel tinTucViewModel;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LuuTinAdapter newsAdapter;
    private List<TinTuc> tinTucList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luu_tin);
        firstInits();
        toolbar.setTitle("Tin đã lưu");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void firstInits() {
        tinTucViewModel = ViewModelProviders.of(this).get(TinTucViewModel.class);
        recyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        tinTucList = new ArrayList<>();
        newsAdapter = new LuuTinAdapter(this,tinTucList,tinTucViewModel);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        tinTucViewModel.getTintuclist().observe(this, new Observer<List<TinTuc>>() {
            @Override
            public void onChanged(@Nullable List<TinTuc> tinTucs) {
                tinTucList.clear();
                tinTucList.addAll(tinTucs);
                newsAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
