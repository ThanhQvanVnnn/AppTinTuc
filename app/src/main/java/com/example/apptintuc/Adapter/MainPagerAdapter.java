package com.example.apptintuc.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.apptintuc.DanhMucTinTucFragment;
import com.example.apptintuc.Object.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<DanhMucTinTucFragment> fragmentList = new ArrayList<>();
    private List<DanhMuc> tabTitles = new ArrayList<>();



    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("idDanhMuc",tabTitles.get(i).getIdDanhMuc());
        DanhMucTinTucFragment danhMucTinTucFragment =  fragmentList.get(i);
        danhMucTinTucFragment.setArguments(bundle);
        return danhMucTinTucFragment;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position).getTenDanhMuc();
    }
    public void addFragment(DanhMucTinTucFragment fragment, DanhMuc danhMucs) {
        fragmentList.add(fragment);
        tabTitles.add(danhMucs);
    }
}
