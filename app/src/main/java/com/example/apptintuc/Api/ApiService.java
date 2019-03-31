package com.example.apptintuc.Api;

import com.example.apptintuc.Object.DanhMuc;
import com.example.apptintuc.Object.TinTuc;

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
}
