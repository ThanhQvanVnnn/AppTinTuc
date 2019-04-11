package com.example.apptintuc.GetDataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.apptintuc.Object.TinTuc;

@Database(entities = {TinTuc.class},version = 1)
public abstract class RoomGetDB extends RoomDatabase {
    public  abstract getLuTinDao getLuTinDao();
    private static volatile RoomGetDB INSTANCE;
    static RoomGetDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomGetDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomGetDB.class, "luutin_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
