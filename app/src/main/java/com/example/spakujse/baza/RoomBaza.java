package com.example.spakujse.baza;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.spakujse.dao.StavkeDao;
import com.example.spakujse.model.Stavke;

// Anotacija @Database definiše ovu klasu kao Room bazu podataka
@Database(entities = Stavke.class, version = 1, exportSchema = false)
public abstract class RoomBaza extends RoomDatabase {

    private static RoomBaza baza;
    private static String BAZAPODATAKA = "SpakujSe";

    // Metoda za dobijanje instance baze podataka (singleton šema)
    public synchronized  static RoomBaza getInstance(Context context){
        // Proverava da li je baza već kreirana
        if(baza == null){
            // Kreira novu instancu baze ako nije već inicijalizovana
            baza = Room.databaseBuilder(context.getApplicationContext(),// Dozvoljava upite na glavnoj niti (ne preporučuje se za velike baze zbog performansi)
            RoomBaza.class,BAZAPODATAKA).allowMainThreadQueries().fallbackToDestructiveMigration().build();// Omogućava destruktivnu migraciju (briše stare podatke pri promeni verzije baze)
        }
        return baza;
    }

    // Apstraktna metoda za dobijanje DAO (Data Access Object) interfejsa
    public abstract StavkeDao glDao();
}
