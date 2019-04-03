package com.example.apptintuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.apptintuc.CustomView.CustomEditText;
import com.example.apptintuc.CustomView.PassWordEditText;
import com.example.apptintuc.Object.EditTextInPut;

public class DangNhap extends AppCompatActivity  implements View.OnClickListener {

    private CustomEditText editTextInPut_email;
    private PassWordEditText passWordEditText_password;
    private Button button_dangnhap,button_dangky;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        firstInits();
        button_dangnhap.setOnClickListener(this);
        button_dangky.setOnClickListener(this);
    }

    private void firstInits() {
        editTextInPut_email = findViewById(R.id.email);
        passWordEditText_password = findViewById(R.id.passWord);
        button_dangnhap = findViewById(R.id.button_Login);
        button_dangky = findViewById(R.id.button_register);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Login:

                break;
            case R.id.button_register:
                Intent intent = new Intent(this,DangKy.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
