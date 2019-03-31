package com.example.apptintuc.GetDataBase;

import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.Api.BuildApi;

public class FromRepository {
    private static final String BASE_URL = "http://192.168.217.2/API/loaisanpham.php/";
    public static ApiService getApiService(){
        return BuildApi.getClient(BASE_URL).create(ApiService.class);
    }
}
