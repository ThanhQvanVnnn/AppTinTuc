package com.example.apptintuc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DanhMucTinTucFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danhmuctintuc_fragment_layout,container,false);
        TextView textView = view.findViewById(R.id.text);
        Bundle bundle=getArguments();
        int id_danhmuc = bundle.getInt("idDanhMuc");
        textView.setText(id_danhmuc+"");
        return view;
    }
}
