package com.example.apptintuc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.CustomView.CustomEditText;
import com.example.apptintuc.CustomView.PassWordEditText;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.EditTextInPut;
import com.example.apptintuc.Object.User;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity  implements View.OnClickListener, View.OnFocusChangeListener {

    private CustomEditText editTextInPut_email;
    private PassWordEditText passWordEditText_password;
    private Button button_dangnhap,button_dangky;
    private Toolbar toolbar;
    private TextInputLayout textInputLayout_email,textInputLayout_pass,textInputLayout_kiemtradangnhap;
    private ApiService apiService;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        firstInits();
        button_dangnhap.setOnClickListener(this);
        button_dangky.setOnClickListener(this);
        editTextInPut_email.setOnFocusChangeListener(this);
        passWordEditText_password.setOnFocusChangeListener(this);
    }

    private void firstInits() {
        editTextInPut_email = findViewById(R.id.email);
        passWordEditText_password = findViewById(R.id.passWord);
        button_dangnhap = findViewById(R.id.button_Login);
        button_dangky = findViewById(R.id.button_register);
        toolbar = findViewById(R.id.toolbar);
        textInputLayout_email = findViewById(R.id.inputNhapEmail);
        textInputLayout_pass = findViewById(R.id.inputNhapPass);
        textInputLayout_kiemtradangnhap = findViewById(R.id.inputKiemtradangnhap);
        apiService = FromRepository.getApiService();
        dialog = new SpotsDialog(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Login:
                dialog.show();
                String email = editTextInPut_email.getText().toString().trim();
                String password = passWordEditText_password.getText().toString().trim();
                apiService.getUser("DangNhap",email,password).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(DangNhap.this);
                        User myObject = response.body();
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(myObject);
                        prefsEditor.putString("user", json);
                        prefsEditor.commit();
                        Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String chuoi;
        switch (v.getId()) {
            case R.id.email:
                if (!hasFocus) {
                    chuoi = ((EditText) v).getText().toString();
                    Boolean kiemtra = Patterns.EMAIL_ADDRESS.matcher(chuoi).matches();
                    if (chuoi.trim().equals("")) {
                        textInputLayout_email.setErrorEnabled(true);
                        textInputLayout_email.setError("Vui lòng không bỏ trống");
                    } else if (!kiemtra) {
                        textInputLayout_email.setErrorEnabled(true);
                        textInputLayout_email.setError("Địa chỉ email sai");
                    } else {
                        textInputLayout_email.setErrorEnabled(false);
                        textInputLayout_email.setError("");
                    }
                }

                break;

            case R.id.passWord:
                chuoi = ((EditText) v).getText().toString();
                if (!hasFocus) {
                    if (chuoi.trim().equals("")) {
                        textInputLayout_pass.setErrorEnabled(true);
                        textInputLayout_pass.setError("Vui lòng không bỏ trống");
                    } else {
                        textInputLayout_pass.setErrorEnabled(false);
                        textInputLayout_pass.setError("");
                    }
                }
                break;
        }
    }
}
