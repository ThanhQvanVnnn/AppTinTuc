package com.example.apptintuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.apptintuc.CustomView.CustomEditText;
import com.example.apptintuc.CustomView.PassWordEditText;
import com.example.apptintuc.Object.EditTextInPut;
import com.example.apptintuc.R;

public class DangKy extends AppCompatActivity implements View.OnClickListener {

    private CustomEditText editTextInPut_email,editTextInPut_userName;
    private PassWordEditText passWordEditText_pass,passWordEditText_repass;
    private Button button_register;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        firstInit();
        button_register.setOnClickListener(this);
    }

    private void firstInit() {
        editTextInPut_email = findViewById(R.id.email);
        editTextInPut_userName = findViewById(R.id.userName);
        passWordEditText_pass = findViewById(R.id.passWord);
        passWordEditText_repass = findViewById(R.id.nhaplai_passWord);
        button_register = findViewById(R.id.button_register);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:

                break;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
