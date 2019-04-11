package com.example.apptintuc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptintuc.Adapter.MainPagerAdapter;
import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.DanhMuc;
import com.example.apptintuc.Object.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private List<DanhMucTinTucFragment> danhMucTinTucFragmentList;
    private List<DanhMuc> danhmuc;
    private MainPagerAdapter mainPagerAdapter;
    private TabLayout tabLayout;
    private ApiService apiService;
    private TextView ten_user;
    AlertDialog dialog;
    public static User user;
    SharedPreferences mPrefs ;
    View header ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstInits();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void firstInits() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        user = new User();
        dialog = new SpotsDialog(this);
        dialog.show();
        apiService = FromRepository.getApiService();
        danhMucTinTucFragmentList = new ArrayList<>();
        header = navigationView.getHeaderView(0);
        ten_user = header.findViewById(R.id.tenUser);
        danhmuc = new ArrayList<>();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        apiService.getDanhMuc("LayDanhSachDanhMuc").enqueue(new Callback<List<DanhMuc>>() {
            @Override
            public void onResponse(Call<List<DanhMuc>> call, Response<List<DanhMuc>> response) {
                dialog.dismiss();
                danhmuc.addAll(response.body());
                viewPager.setAdapter(mainPagerAdapter);
                for (DanhMuc danhMuc : danhmuc) {
                    mainPagerAdapter.addFragment(new DanhMucTinTucFragment(), danhMuc);
                }
                mainPagerAdapter.notifyDataSetChanged();
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<List<DanhMuc>> call, Throwable t) {
                Log.d("kiemtra","Lỗi : Lấy Danh Mục " + t.getMessage().toString());
            }
        });

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        user = gson.fromJson(json, User.class);
        LayThongTinUser();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menuu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,TimKiemActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.search) {

             intent = new Intent(this,TimKiemActivity.class);
             startActivity(intent);


        } else if (id == R.id.luutin) {

            intent = new Intent(this,LuuTin.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            user =null;
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.remove("user");
            editor.commit();
            ten_user.setText("Bạn cần đăng nhập");
            ten_user.setTextColor(getResources().getColor(R.color.colorAccent));
            LayThongTinUser();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.login) {
            intent = new Intent(this,DangNhap.class);
            startActivityForResult(intent,1);

        }else if (id == R.id.register) {
            intent = new Intent(this,DangKy.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void LayThongTinUser(){

        if(user == null){
            navigationView.getMenu().findItem(R.id.login).setVisible(true);
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.register).setVisible(true);
        }else {
            ten_user.setText(user.getHoten());
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.register).setVisible(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Gson gson = new Gson();
                String json = mPrefs.getString("user", "");
                user = gson.fromJson(json, User.class);
                LayThongTinUser();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        user = gson.fromJson(json, User.class);
        LayThongTinUser();

    }
}
