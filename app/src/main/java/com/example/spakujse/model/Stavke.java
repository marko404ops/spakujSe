package com.example.spakujse.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stavke")
public class Stavke implements Serializable {

//    podaci koji ce se upisivati u bazu podataka

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "naziv")
    String naziv;

    @ColumnInfo(name = "kategorija")
    String kategorija;

//    dodato predstavlja podatke unijete od strane korisnika a ne hardcodovane vrijednostri
    @ColumnInfo(name = "dodato")
    String dodato;

    @ColumnInfo(name = "izabrano")
    Boolean izabrano = false;

    public Stavke() {
    }

    public Stavke(String naziv, String kategorija, Boolean izabrano) {
        this.dodato = "hardcoded";
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.izabrano = izabrano;
    }

    public Stavke(String naziv, String kategorija, String dodato, Boolean izabrano) {
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.dodato = dodato;
        this.izabrano = izabrano;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getDodato() {
        return dodato;
    }

    public void setDodato(String dodato) {
        this.dodato = dodato;
    }

    public Boolean getIzabrano() {
        return izabrano;
    }

    public void setIzabrano(Boolean izabrano) {
        this.izabrano = izabrano;
    }
}
