package com.example.apptintuc;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptintuc.Adapter.CommentAdapter;
import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.BinhLuan;
import com.example.apptintuc.Object.EditTextInPut;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton send;
    private EditTextInPut input_binhluan;
    private RecyclerView recyclerView;
    private ImageView back;
    private TextView TextTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<BinhLuan> binhLuanList;
    private ApiService apiService;
    private CommentAdapter commentAdapter;

    String title;
    int id_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        firstInits();
        send.setOnClickListener(this);
        back.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                apiService.getBinhLuanTheoMaTin("LayDanhSachBinhLuanTheoMa", String.valueOf(id_new)).enqueue(new Callback<List<BinhLuan>>() {
                    @Override
                    public void onResponse(Call<List<BinhLuan>> call, Response<List<BinhLuan>> response) {
                        binhLuanList.clear();
                        binhLuanList.addAll(response.body());
                        commentAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<BinhLuan>> call, Throwable t) {
                        Log.d("kiemtra","Lấy Bình luận "+t.getMessage());
                    }
                });
            }
        });
    }

    private void firstInits() {
        send = findViewById(R.id.send_button);
        input_binhluan = findViewById(R.id.edittext_input_comment);
        recyclerView = findViewById(R.id.recycle_binhluan);
        back = findViewById(R.id.imageback);
        TextTitle = findViewById(R.id.textTitle);
        swipeRefreshLayout = findViewById(R.id.swipeBinhluan);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimary));
        binhLuanList = new ArrayList<>();
       Intent intent =getIntent();
        id_new = intent.getIntExtra("maTin",-1);
       title = intent.getStringExtra("title");
       TextTitle.setText(title);
       apiService = FromRepository.getApiService();
       apiService.getBinhLuanTheoMaTin("LayDanhSachBinhLuanTheoMa", String.valueOf(id_new)).enqueue(new Callback<List<BinhLuan>>() {
           @Override
           public void onResponse(Call<List<BinhLuan>> call, Response<List<BinhLuan>> response) {
               binhLuanList.addAll(response.body());
               binhLuanList = Lists.reverse(binhLuanList);
               commentAdapter = new CommentAdapter(CommentActivity.this,binhLuanList);
               recyclerView.setAdapter(commentAdapter);
               recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this,LinearLayoutManager.VERTICAL,false));
           }

           @Override
           public void onFailure(Call<List<BinhLuan>> call, Throwable t) {
               Log.d("kiemtra","Lấy Bình luận "+t.getMessage());
           }
       });
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        input_binhluan.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_button:
                if(MainActivity.user!=null) {
                    final String binhluan = input_binhluan.getText().toString();
                    Calendar calendar = Calendar.getInstance();
                    final java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
                    if (binhluan.length() == 0) {
                        Toast.makeText(this, "Vui lòng không để trống ", Toast.LENGTH_SHORT).show();
                    } else {
                        apiService.ThemBinhLuan("ThemBinhLuan", String.valueOf(id_new), "thanhquanqwer@gmail.com", startDate.toString(), "quanle", binhluan).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                BinhLuan binhLuan = new BinhLuan("thanhquanqwer@gmail.com", startDate.toString(), binhluan, id_new, "quanle");
                                if (response.body().equals("fail")) {
                                    Toast.makeText(CommentActivity.this, "Bình luân thất bại", Toast.LENGTH_SHORT).show();
                                } else if (response.body().equals("success")) {
                                    binhLuanList.add(0, binhLuan);
                                    commentAdapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(CommentActivity.this, "Bình luân thành công", Toast.LENGTH_SHORT).show();
                                    hideKeyboard(CommentActivity.this);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("kiemtra", "Lỗi : Thêm Bình Luận " + t.getMessage());
                            }
                        });
                        input_binhluan.setText("");
                    }
                }else {
                    Toast.makeText(this, "Bạn cần đăng nhập để bình luận", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.imageback:
                hideKeyboard(this);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",binhLuanList.size());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;

        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",binhLuanList.size());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
