package com.example.apptintuc;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptintuc.Adapter.NewsAdapter;
import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.TinTuc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<TinTuc> tinTucs;
    private NewsAdapter newsAdapter;
    private ApiService apiService;
    private TextView khongtimthay;
    String texttimkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        firstInits();
        toolbar.setTitle("Tìm kiếm nội dung");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                apiService.getTimKiem("getTimKiem",texttimkiem).enqueue(new Callback<List<TinTuc>>() {
                    @Override
                    public void onResponse(Call<List<TinTuc>> call, Response<List<TinTuc>> response) {
                        if(response.body().size()!=0) {
                            tinTucs.addAll(response.body());
                            newsAdapter.notifyDataSetChanged();
                            khongtimthay.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else {
                            tinTucs.clear();
                            newsAdapter.notifyDataSetChanged();
                            khongtimthay.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<TinTuc>> call, Throwable t) {
                        Log.d("kiemtra","tìm kiêm "+ t.getMessage());
                    }
                });
            }
        });
    }

    private void firstInits() {
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimary));
        recyclerView = findViewById(R.id.recyclerview);
        khongtimthay = findViewById(R.id.khongtimthay);
        tinTucs = new ArrayList<>();
        apiService = FromRepository.getApiService();
        newsAdapter = new NewsAdapter(TimKiemActivity.this, tinTucs);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimKiemActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem itSearch = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itSearch);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        texttimkiem = s;
        apiService.getTimKiem("getTimKiem",s).enqueue(new Callback<List<TinTuc>>() {
            @Override
            public void onResponse(Call<List<TinTuc>> call, Response<List<TinTuc>> response) {
                if(response.body().size()!=0) {
                    tinTucs.addAll(response.body());
                    newsAdapter.notifyDataSetChanged();
                    khongtimthay.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    tinTucs.clear();
                    newsAdapter.notifyDataSetChanged();
                    khongtimthay.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<TinTuc>> call, Throwable t) {
                Log.d("kiemtra","tìm kiêm "+ t.getMessage());
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
