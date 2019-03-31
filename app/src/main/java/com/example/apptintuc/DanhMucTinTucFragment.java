package com.example.apptintuc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptintuc.Adapter.NewsAdapter;
import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.TinTuc;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhMucTinTucFragment extends Fragment implements View.OnClickListener {
    private KenBurnsView kenBurnsView;
    private RecyclerView recyclerView;
    private TextView firstTitle;
    private List<TinTuc> tinTucs;
    private ApiService apiService;
    private NewsAdapter newsAdapter;
    private TinTuc tinTuc;
    String id_danhmuc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danhmuctintuc_fragment_layout,container,false);
        Bundle bundle = getArguments();
        id_danhmuc = bundle.getString("idDanhMuc");
        firstInits(view);
        return view;
    }

    private void firstInits(View view) {
        kenBurnsView = view.findViewById(R.id.image_articel_first);
        recyclerView = view.findViewById(R.id.recycle_view);
        firstTitle = view.findViewById(R.id.title_article_first);
        apiService = FromRepository.getApiService();
        tinTucs = new ArrayList<>();

        apiService.getTinTuc("LayDanhSachTinTuc",id_danhmuc).enqueue(new Callback<List<TinTuc>>() {
            @Override
            public void onResponse(Call<List<TinTuc>> call, Response<List<TinTuc>> response) {
                tinTucs.addAll(response.body());
                tinTuc = tinTucs.get(0);
                Picasso.get().load(tinTuc.getImg()).into(kenBurnsView);
                firstTitle.setText(tinTuc.getTieude());
                newsAdapter = new NewsAdapter(getContext(),tinTucs);
                recyclerView.setAdapter(newsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onFailure(Call<List<TinTuc>> call, Throwable t) {
                Log.d("kiemtra","Lỗi :"+t.getMessage().toString());
            }
        });
        kenBurnsView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_article_first:
            case R.id.image_articel_first:
                Intent intent = new Intent(getContext(),DetailArticle.class);
                intent.putExtra("idNews",tinTuc.getId_tin());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
        }
    }
}
