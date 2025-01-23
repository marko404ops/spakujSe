package com.example.spakujse.dao;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spakujse.model.Stavke;

import java.util.List;

@Dao
public interface StavkeDao {
//sve naredbe koje ce se koristiti za bazu podataka
    @Insert(onConflict = REPLACE)
    void sacuvajPodatke(Stavke stavke);

    @Query("select * from stavke where kategorija=:kategorija order by id asc")
    List<Stavke> dobaviSve(String kategorija);

    @Delete
    void obrisi(Stavke stavke);

    @Query("update stavke set izabrano=:izabrano where ID=:id ")
    void odabir(int id, boolean izabrano);

    @Query("select count(*) from stavke")
    Integer dobaviBrojStavki();

    @Query("delete from stavke where dodato=:dodato")
    Integer obrisiSveHardcodedStavke(String dodato);

    @Query("delete from stavke where kategorija=:kategorija")
    Integer obrisiSvePoKategoriji(String kategorija);

    @Query("delete from stavke where kategorija=:kategorija and dodato=:dodato")
    Integer obrisiSvePoKategorijiIDodato(String kategorija, String dodato);

    @Query("select * from stavke where izabrano=:izabrano order by id asc")
    List<Stavke> dobaviSveSelektovano(Boolean izabrano);
}
