package com.example.apptintuc.GetDataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.apptintuc.Object.TinTuc;

import java.util.List;

@Dao
public interface getLuTinDao {

    @Insert
    void insert(TinTuc tinTuc);

    @Query("SELECT * from tin_tuc")
    LiveData<List<TinTuc>> getAllTinTuc();

    @Query("DELETE FROM tin_tuc WHERE id_tin=:id")
    void delete(int id );


}
