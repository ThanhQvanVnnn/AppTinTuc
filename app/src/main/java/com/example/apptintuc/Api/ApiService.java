package com.example.apptintuc.Api;

import com.example.apptintuc.Object.BinhLuan;
import com.example.apptintuc.Object.DanhMuc;
import com.example.apptintuc.Object.TinTuc;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST(" ")
    Call<List<DanhMuc>> getDanhMuc(@Field("ham") String ham);

    @FormUrlEncoded
    @POST(" ")
    Call<List<TinTuc>> getTinTuc(@Field("ham") String ham, @Field("maLoai") String maloai);

    @FormUrlEncoded
    @POST(" ")
    Call<List<BinhLuan>> getBinhLuanTheoMaTin(@Field("ham") String ham, @Field("maTin") String maTin);

    @FormUrlEncoded
    @POST(" ")
    Call<List<BinhLuan>> ThemBinhLuan(@Field("ham") String ham, @Field("maTin") String matin
            ,@Field("Email") String email,@Field("Date") String date
            ,@Field("idNguoiDung") String id,@Field("noiDung") String noidung);
}
