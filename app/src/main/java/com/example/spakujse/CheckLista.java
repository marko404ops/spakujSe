package com.example.spakujse;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.spakujse.adapter.CheckListaAdapter;
import com.example.spakujse.baza.RoomBaza;
import com.example.spakujse.konstante.Konstanta;
import com.example.spakujse.model.Stavke;

import java.util.ArrayList;
import java.util.List;

// Klasa CheckLista predstavlja aktivnost koja prikazuje i omogućava upravljanje stavkama u okviru određene kategorije.

public class CheckLista extends AppCompatActivity {

    RecyclerView recyclerView;
    CheckListaAdapter checkListaAdapter;
    RoomBaza bazapodataka;
    List<Stavke> stavkeList = new ArrayList<>();
    String header, show;
    EditText textAdd;
    Button btnAdd;
    LinearLayout linearLayout;

    // Metoda za kreiranje menija sa opcijama.
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        //    sakrivanje stavki u meniju u zavisnosti u kojoj smo kategoriji
        if (Konstanta.MOJ_ODABIR.equals(header)){
            menu.getItem(0).setVisible(false);
            menu.getItem(2).setVisible(false);

        }else if (Konstanta.MOJA_LISTA.equals(header)){
            menu.getItem(1).setVisible(false);

        }
//        pretraga
        MenuItem menuItem = menu.findItem(R.id.pretraga);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            //            pretraga
            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrira stavke na osnovu unetog teksta.
                List<Stavke> finalList = new ArrayList<>();
                for (Stavke stavke : stavkeList){
                    if (stavke.getNaziv().toLowerCase().startsWith(newText.toLowerCase())){
                        finalList.add(stavke);
                    }
                }
                updateRecycler(finalList);

                return false;
            }
        });

        return true;
    }

//    dodavanje funkcionalnosti stavkama iz padajuceg menija
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem stavka) {
//        Intent intent = new Intent(this, CheckLista.class);
//        Podaci podaci = new Podaci(bazapodataka, this);
//
//            switch (stavka.getItemId()){
//            case R.id.:
//                intent.putExtra(Konstanta.HEADER, Konstanta.MOJ_ODABIR);
//                intent.putExtra(Konstanta.SHOW, Konstanta.FALSE_STRING);
//                startActivityForResult(intent,101 );
//                return true;
//
//            case R.id.mojaLista:
//                intent.putExtra(Konstanta.HEADER, Konstanta.MOJA_LISTA);
//                intent.putExtra(Konstanta.SHOW, Konstanta.FALSE_STRING);
//                startActivity(intent);
//                return true;
//
//            case R.id.reset:
//                new AlertDialog.Builder(this)
//                        .setTitle("Vrati Na Početno Stanje")
//                        .setMessage("Povratkom na početno stanje, sve stavke koje ste unijeli u ("+header+") biće obrisane")
//                        .setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                podaci.cuvajPodatkePoKategoriji(header, false);
//                                stavkeList = bazapodataka.glDao().dobaviSve(header);
//                                updateRecycler(stavkeList);
//                            }
//                        }).setNegativeButton("Otkaži", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        }).setIcon(R.drawable.alert)
//                        .show();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(stavka);
//        }
//    }
//    kada odselektujemo nesto iz mog odabira to isto mora da se odselektuje iz odgovarajuce kategorije
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101){
            stavkeList = bazapodataka.glDao().dobaviSve(header);
            updateRecycler(stavkeList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_lista);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        header = intent.getStringExtra(Konstanta.HEADER);
        show = intent.getStringExtra(Konstanta.SHOW);
        if (actionBar != null) {
            actionBar.setTitle(header);// Postavlja naslov ActionBar-a.
        }

        textAdd = findViewById(R.id.textAdd);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayout = findViewById(R.id.LinearLayout);
        bazapodataka = RoomBaza.getInstance(this);

        if (Konstanta.FALSE_STRING.equals(show)){
            linearLayout.setVisibility(View.GONE);
            stavkeList = bazapodataka.glDao().dobaviSveSelektovano(true); // Prikazuje samo selektovane stavke.
        }else {
            stavkeList = bazapodataka.glDao().dobaviSve(header);
        }
        updateRecycler(stavkeList);

        // Klik događaj za dodavanje nove stavke.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stavkaIme = textAdd.getText().toString();
                if (!stavkaIme.isEmpty()){
                    dodajNovuStavku(stavkaIme);
                    Toast.makeText(CheckLista.this, "Dodato", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CheckLista.this, "Unesite stavku", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Završava aktivnost kada korisnik pritisne dugme za povratak.
        finish();
        return true;
    }

    // Metoda za dodavanje nove stavke u bazu podataka.
    private void dodajNovuStavku(String stavkaIme){
        Stavke stavka = new Stavke();
        stavka.setIzabrano(false);
        stavka.setKategorija(header);
        stavka.setNaziv(stavkaIme);
        stavka.setDodato(Konstanta.USER);
        bazapodataka.glDao().sacuvajPodatke(stavka);
        stavkeList = bazapodataka.glDao().dobaviSve(header);
        updateRecycler(stavkeList);
        recyclerView.scrollToPosition(checkListaAdapter.getItemCount()-1);
        textAdd.setText("");
    }

    // Ažurira RecyclerView sa novom listom stavki.
    private void updateRecycler(List<Stavke> stavkeList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListaAdapter = new CheckListaAdapter(CheckLista.this, stavkeList, bazapodataka, show);
        recyclerView.setAdapter(checkListaAdapter);
    }
}

