package com.example.spakujse;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spakujse.adapter.Adapter;
import com.example.spakujse.baza.RoomBaza;
import com.example.spakujse.konstante.Konstanta;
import com.example.spakujse.podaci.Podaci;

import java.util.ArrayList;
import java.util.List;
// Glavna aktivnost aplikacije - prikazuje osnovni interfejs sa RecyclerView elementima.

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> naslovi;
    List<Integer> slike;
    Adapter adapter;// Adapter koji povezuje podatke sa RecyclerView-om.
    RoomBaza bazapozadataka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Postavlja izgled aktivnosti iz XML fajla.
//        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerView);

        dodajNaslove();
        dodajSlike();
        cuvajPodatkeAplikacije();
        bazapozadataka = RoomBaza.getInstance(this);
        System.out.println("-------------------->" + bazapozadataka.glDao().dobaviSveSelektovano(false).get(0).getNaziv());
        // Postavlja adapter i menadžer izgleda za RecyclerView.
        adapter = new Adapter(this, naslovi, slike, MainActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void cuvajPodatkeAplikacije(){
        // Čuva podatke u SharedPreferences i proverava da li je aplikacija prvi put pokrenuta.

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        bazapozadataka = RoomBaza.getInstance(this);
        Podaci podaci = new Podaci(bazapozadataka);
        int poslednjaV = preferences.getInt(Podaci.POSLEDNJA_VERZIJA, 0);
        if (!preferences.getBoolean(Konstanta.PRVO_POKRETANJE, false)){
            // Prvo pokretanje aplikacije - čuva osnovne podatke.
            podaci.cuvajPodatke();
            editor.putBoolean(Konstanta.PRVO_POKRETANJE, true);
            editor.commit();
        }else if(poslednjaV<Podaci.NOVA_VERZIJA){
            // Ako je aplikacija ažurirana, briše stare podatke i čuva nove.
            bazapozadataka.glDao().obrisiSveHardcodedStavke(Konstanta.SYSTEM);
            podaci.cuvajPodatke();
            editor.putInt(Podaci.POSLEDNJA_VERZIJA,Podaci.NOVA_VERZIJA);
            editor.commit();
        }
    }


    private void dodajNaslove(){
        naslovi = new ArrayList<>();
        naslovi.add(Konstanta.OSNOVNE_POTREBE);
        naslovi.add(Konstanta.ODJECA);
        naslovi.add(Konstanta.HIGIJENA);
        naslovi.add(Konstanta.ZDRAVLJE);
        naslovi.add(Konstanta.TEHNOLOGIJA);
        naslovi.add(Konstanta.HRANA);
        naslovi.add(Konstanta.MOJA_LISTA);
        naslovi.add(Konstanta.MOJ_ODABIR);
//        dodati za onoliko koliko bokseva hocu
    }

    private void dodajSlike(){
        slike = new ArrayList<>();
        slike.add(R.drawable.s1);
        slike.add(R.drawable.s8);
        slike.add(R.drawable.s4);
        slike.add(R.drawable.s3);
        slike.add(R.drawable.s2);
        slike.add(R.drawable.s7);
        slike.add(R.drawable.s9);
        slike.add(R.drawable.s6);
//        dodati ostale slike za bokseve
    }


}