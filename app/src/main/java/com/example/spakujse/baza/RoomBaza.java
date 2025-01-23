package com.example.spakujse.baza;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.spakujse.dao.StavkeDao;
import com.example.spakujse.model.Stavke;

@Database(entities = Stavke.class, version = 1, exportSchema = false)
public abstract class RoomBaza extends RoomDatabase {

    private static RoomBaza baza;
    private static String BAZAPODATAKA = "SpakujSe";

    public synchronized  static RoomBaza getInstance(Context context){
        if(baza == null){
            baza = Room.databaseBuilder(context.getApplicationContext(),
            RoomBaza.class,BAZAPODATAKA).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return baza;
    }

    public abstract StavkeDao glDao();
}
