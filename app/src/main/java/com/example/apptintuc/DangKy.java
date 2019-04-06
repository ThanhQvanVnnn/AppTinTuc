package com.example.apptintuc;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKy extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private CustomEditText customEditText_email,customEditText_userName,customEditText_sdt;
    private PassWordEditText passWordEditText_pass,passWordEditText_repass;
    private TextInputLayout editTextInPut_userName,editTextInPut_email,editTextInPut_matkhau,editTextInPut_nhaplaimatkhau
            ,editTextInPut_sdt;
    private Button button_register;
    private Toolbar toolbar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        firstInit();
        button_register.setOnClickListener(this);
        customEditText_userName.setOnFocusChangeListener(this);
        customEditText_email.setOnFocusChangeListener(this);
        passWordEditText_repass.setOnFocusChangeListener(this);
        customEditText_sdt.setOnFocusChangeListener(this);
        passWordEditText_pass.setOnFocusChangeListener(this);
    }

    private void firstInit() {
        customEditText_email = findViewById(R.id.email);
        customEditText_userName = findViewById(R.id.userName);
        customEditText_sdt = findViewById(R.id.sdt);
        passWordEditText_pass = findViewById(R.id.passWord);
        passWordEditText_repass = findViewById(R.id.nhaplai_passWord);
        editTextInPut_userName = findViewById(R.id.edittextinputuserName);
        editTextInPut_email = findViewById(R.id.edittextinput_email);
        editTextInPut_matkhau = findViewById(R.id.edittextinput_matkhau);
        editTextInPut_nhaplaimatkhau = findViewById(R.id.edittextinput_nhaplaimatkhau);
        editTextInPut_sdt = findViewById(R.id.edittextinput_sodienthoai);
        apiService = FromRepository.getApiService();

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
                String email = customEditText_email.getText().toString().trim();
                String userName = customEditText_userName.getText().toString().trim();
                String sdt = customEditText_sdt.getText().toString().trim();
                String pass = passWordEditText_pass.getText().toString().trim();
                apiService.themUser("DangKy",email,pass,userName,sdt).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        if(result.equals("success")){
                            Toast.makeText(DangKy.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKy.this,DangNhap.class);
                            startActivity(intent);
                            finish();
                        }else if(result.equals("fail")){
                            Toast.makeText(DangKy.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("kiemtra","đăng kí "+t.getMessage());
                    }
                });
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
        int id = v.getId();
        String chuoi;
        if(!hasFocus){
            chuoi = ((EditText) v).getText().toString();
            switch (id){
                case R.id.email:
                    Boolean kiemtra = Patterns.EMAIL_ADDRESS.matcher(chuoi).matches();
                    if (chuoi.trim().equals("")) {
                        editTextInPut_email.setErrorEnabled(true);
                        editTextInPut_email.setError("Vui lòng không bỏ trống");
                    } else if (!kiemtra) {
                        editTextInPut_email.setErrorEnabled(true);
                        editTextInPut_email.setError("Địa chỉ email sai");
                    } else {
                        editTextInPut_email.setErrorEnabled(false);
                        editTextInPut_email.setError("");
                    }
                    break;
                case R.id.userName:
                    chuoi = ((EditText) v).getText().toString();
                        if (chuoi.trim().equals("")) {
                            editTextInPut_userName.setErrorEnabled(true);
                            editTextInPut_userName.setError("Vui lòng không bỏ trống");
                        } else {
                            editTextInPut_userName.setErrorEnabled(false);
                            editTextInPut_userName.setError("");
                        }

                    break;
                case R.id.sdt:
                    chuoi = ((EditText) v).getText().toString();
                        if (chuoi.trim().equals("")) {
                            editTextInPut_sdt.setErrorEnabled(true);
                            editTextInPut_sdt.setError("Vui lòng không bỏ trống");
                        } else {
                            editTextInPut_sdt.setErrorEnabled(false);
                            editTextInPut_sdt.setError("");
                        }

                    break;
                case R.id.passWord:
                    chuoi = ((EditText) v).getText().toString().trim();
                        if (chuoi.trim().equals("")) {
                            editTextInPut_matkhau.setErrorEnabled(true);
                            editTextInPut_matkhau.setError("Vui lòng không bỏ trống");
                        } else {
                            editTextInPut_matkhau.setErrorEnabled(false);
                            editTextInPut_matkhau.setError("");
                        }

                    break;
                case R.id.nhaplai_passWord:
                   String chuoi1 = passWordEditText_pass.getText().toString().trim();
                    chuoi = ((EditText) v).getText().toString().trim();
                    if (chuoi.trim().equals("")) {
                        editTextInPut_nhaplaimatkhau.setErrorEnabled(true);
                        editTextInPut_nhaplaimatkhau.setError("Vui lòng không bỏ trống");
                    }else if(!chuoi1.equals(chuoi)){
                        editTextInPut_nhaplaimatkhau.setErrorEnabled(true);
                        editTextInPut_nhaplaimatkhau.setError("Mật khẩu nhập lại không đúng");
                    }else {
                        editTextInPut_nhaplaimatkhau.setErrorEnabled(false);
                        editTextInPut_nhaplaimatkhau.setError("");
                    }
                    break;
            }
        }
    }
}
