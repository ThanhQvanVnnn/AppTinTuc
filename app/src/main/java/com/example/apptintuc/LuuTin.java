package com.example.apptintuc;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.apptintuc.GetDataBase.TinTucViewModel;
import com.example.apptintuc.Object.TinTuc;

import java.util.List;

public class LuuTin extends AppCompatActivity {

    private TinTucViewModel tinTucViewModel;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

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
        tinTucViewModel.getTintuclist().observe(this, new Observer<List<TinTuc>>() {
            @Override
            public void onChanged(@Nullable List<TinTuc> tinTucs) {

            }
        });
    }
}
