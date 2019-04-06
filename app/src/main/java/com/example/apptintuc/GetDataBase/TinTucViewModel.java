package com.example.apptintuc.GetDataBase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.apptintuc.Object.TinTuc;

import java.util.List;

public class TinTucViewModel extends AndroidViewModel {
    private FromRepository fromRepository;
    private LiveData<List<TinTuc>> tintuclist;
    public TinTucViewModel(@NonNull Application application) {
        super(application);
        fromRepository = new FromRepository(application);
        tintuclist = fromRepository.getTintuclist();
    }
    public void insert_tintuc(TinTuc tinTuc){
        fromRepository.insert_tintuc(tinTuc);
    }
    public void detete_tintuc(int integer){
        fromRepository.delete_tintuc(integer);
    }

    public LiveData<List<TinTuc>> getTintuclist() {
        return tintuclist;
    }
}
