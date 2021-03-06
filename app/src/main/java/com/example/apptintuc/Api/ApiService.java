package com.example.apptintuc.Api;

import com.example.apptintuc.Object.BinhLuan;
import com.example.apptintuc.Object.DanhMuc;
import com.example.apptintuc.Object.TinTuc;
import com.example.apptintuc.Object.User;

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
    Call<String> ThemBinhLuan(@Field("ham") String ham, @Field("maTin") String matin
            ,@Field("Email") String email,@Field("Date") String date
            ,@Field("idNguoiDung") String id,@Field("noiDung") String noidung);

    @FormUrlEncoded
    @POST(" ")
    Call<User> LayUserTheoId(@Field("ham") String ham, @Field("idUser") String idUser);

    @FormUrlEncoded
    @POST(" ")
    Call<TinTuc> LayTinTheoId(@Field("ham") String ham, @Field("id_new") int idTin);

    @FormUrlEncoded
    @POST(" ")
    Call<List<TinTuc>> getTimKiem(@Field("ham") String ham, @Field("noidung") String noidung);

    @FormUrlEncoded
    @POST(" ")
    Call<User> getUser(@Field("ham") String ham, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST(" ")
    Call<String> themUser(@Field("ham") String ham, @Field("email") String email,
                        @Field("password") String password,@Field("username") String username,@Field("sdt") String sdt);
}
