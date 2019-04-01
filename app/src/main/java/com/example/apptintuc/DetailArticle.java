package com.example.apptintuc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.BinhLuan;
import com.example.apptintuc.Object.EditTextInPut;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailArticle extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private String content,tieude;
    private int id_new;
    private TextView textTile;
    private AlertDialog dialog;
    private NestedScrollView scrollView;
    private LinearLayout topLayout;
    private ImageView imageView_back;
    private Button button_binhluan;
    private RelativeLayout trangbinhluan;
    private ImageView luutrang;
    private EditTextInPut input_binhluan;
    private ImageButton button_submitBinhLuan;
    private TextView soluongbinhluan;
    private View layout_itconBinhLuan;
    private ApiService apiService;
    boolean show = false;
    int socomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        firstInits();
        OnsCroll();
        if(socomment==0){
            soluongbinhluan.setVisibility(View.GONE);
        }else soluongbinhluan.setText(socomment+"");
        button_binhluan.setOnClickListener(this);
        button_submitBinhLuan.setOnClickListener(this);
        luutrang.setOnClickListener(this);
        layout_itconBinhLuan.setOnClickListener(this);
        input_binhluan.setKeyImeChangeListener(new EditTextInPut.KeyImeChange(){
            @Override
            public void onKeyIme(int keyCode, KeyEvent event)
            {
                if (KeyEvent.KEYCODE_BACK == event.getKeyCode())
                {
                    button_binhluan.setVisibility(View.VISIBLE);
                    luutrang.setVisibility(View.VISIBLE);
                    layout_itconBinhLuan.setVisibility(View.VISIBLE);
                    input_binhluan.setVisibility(View.GONE);
                    button_submitBinhLuan.setVisibility(View.GONE);
                }
            }});
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageback:
                finish();
                break;
            case R.id.button_input_comment:
               hienThiEditBinhLuan();
                break;
            case R.id.luutin:

                break;
            case R.id.send_button:
                String binhluan = input_binhluan.getText().toString();
                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
                if(binhluan.length() ==0){
                    Toast.makeText(this, "Vui lòng không để trống ", Toast.LENGTH_SHORT).show();
                }else {
                    apiService.ThemBinhLuan("ThemBinhLuan", String.valueOf(id_new),"thanhquanqwer@gmail.com",startDate.toString(),"quanle",binhluan).enqueue(new Callback<List<BinhLuan>>() {
                        @Override
                        public void onResponse(Call<List<BinhLuan>> call, Response<List<BinhLuan>> response) {
                            List<BinhLuan> binhLuanList = response.body();
                            if(binhLuanList.size()==0){
                                Toast.makeText(DetailArticle.this, "Bình luân thất bại", Toast.LENGTH_SHORT).show();
                            }else {
                                soluongbinhluan.setText(binhLuanList.size()+"");
                                anEditBinhLuan();
                                Toast.makeText(DetailArticle.this, "Bình luân thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<BinhLuan>> call, Throwable t) {
                            Log.d("kiemtra","Lỗi : Thêm Bình Luận " + t.getMessage());
                        }
                    });
                }
                break;
            case R.id.layout_iconbinhluan:
                if(socomment == 0){
                    hienThiEditBinhLuan();
                }else {

                }
                break;
        }
    }

    private void hienThiEditBinhLuan(){
        button_binhluan.setVisibility(View.GONE);
        luutrang.setVisibility(View.GONE);
        layout_itconBinhLuan.setVisibility(View.GONE);
        input_binhluan.setVisibility(View.VISIBLE);
        button_submitBinhLuan.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        input_binhluan.requestFocus();
    }

    private void anEditBinhLuan(){
        hideKeyboard(DetailArticle.this);
        button_binhluan.setVisibility(View.VISIBLE);
        luutrang.setVisibility(View.VISIBLE);
        layout_itconBinhLuan.setVisibility(View.VISIBLE);
        input_binhluan.setVisibility(View.GONE);
        button_submitBinhLuan.setVisibility(View.GONE);
    }

    private void firstInits() {
        webView = findViewById(R.id.webView);
        textTile = findViewById(R.id.textTitle);
        imageView_back = findViewById(R.id.imageback);
        dialog = new SpotsDialog(this);
        scrollView = findViewById(R.id.scrollView);
        topLayout = findViewById(R.id.tool_header);
        apiService = FromRepository.getApiService();
        button_binhluan = findViewById(R.id.button_input_comment);
        trangbinhluan = findViewById(R.id.trangbinhluan);
        luutrang = findViewById(R.id.luutin);
        input_binhluan = findViewById(R.id.edittext_input_comment);
        button_submitBinhLuan = findViewById(R.id.send_button);
        soluongbinhluan = findViewById(R.id.number_binhluan);
        layout_itconBinhLuan = findViewById(R.id.layout_iconbinhluan);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dialog.show();
        content = bundle.getString("Content");
        id_new = bundle.getInt("idNews",-1);
        tieude = bundle.getString("Tieude");
        socomment = bundle.getInt("SizeBinhLuan");
        String mime = "text/html";
        String encoding = "utf-8";
        String style ="<style>img{display: inline;height: auto;max-width: 100%;}</style>";
        textTile.setText(tieude);
        webView.getSettings().setJavaScriptEnabled(true);
        imageView_back.setOnClickListener(this);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.loadDataWithBaseURL(null,style + content, mime, encoding, null);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void slideHideHeader(View view) {
        show = false;
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setEnabled(false);
    }

    public void slideShowHeader(View view) {
        view.setVisibility(View.VISIBLE);
        show = true;
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    public void OnsCroll() {
        if (scrollView != null) {

            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    String TAG = "nested_sync";

                    if (scrollY > oldScrollY) {

                        if(show == false ) {
                            slideShowHeader(topLayout);
                            topLayout.setEnabled(true);
                        }
                    }
                    if (scrollY < oldScrollY) {

                        if (show == true) {
                            slideHideHeader(topLayout);
                            topLayout.setEnabled(false);
                        }
                    }

                    if (scrollY == 0) {
//                        Log.i(TAG, "TOP SCROLL");
                    }

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

//                        Log.i(TAG, "BOTTOM SCROLL");


                    }
                }
            });
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



}
