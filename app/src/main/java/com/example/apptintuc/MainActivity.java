package com.example.apptintuc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.apptintuc.Adapter.MainPagerAdapter;
import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.DanhMuc;

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
    AlertDialog dialog;
    Menu menu;
    MenuItem item_login,item_register,item_logout;

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

//        toolbar.setT;
        dialog = new SpotsDialog(this);
        dialog.show();
        apiService = FromRepository.getApiService();
        danhMucTinTucFragmentList = new ArrayList<>();
        danhmuc = new ArrayList<>();
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu = menuu;
        getMenuInflater().inflate(R.menu.main, menuu);
        item_login = menu.findItem(R.id.login);
        item_register = menu.findItem(R.id.login);
        item_logout = menu.findItem(R.id.logout);
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

        } else if (id == R.id.logout) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.equals("ok")) {
                    item_login.setVisible(false);
                    item_register.setVisible(false);
                    item_logout.setVisible(true);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
