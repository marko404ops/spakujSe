package com.example.spakujse.podaci;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.spakujse.baza.RoomBaza;
import com.example.spakujse.konstante.Konstanta;
import com.example.spakujse.model.Stavke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// Klasa Podaci pruža funkcionalnosti za upravljanje podacima aplikacije, uključujući inicijalizaciju kategorija, čuvanje podataka u bazi, i resetovanje podataka.

//sadrzi sve podatke koji su potrebni
public class Podaci extends Application {

    RoomBaza bazapodataka;
    String kategorija;
    Context context;
    public static final String POSLEDNJA_VERZIJA = "POSLEDNJA_VERZIJA";
    public static final int NOVA_VERZIJA = 1;

    public Podaci(RoomBaza bazapodataka) {
        this.bazapodataka = bazapodataka;
    }

    public Podaci(RoomBaza bazapodataka, Context context) {
        this.bazapodataka = bazapodataka;
        this.context = context;
    }

//        popunjavamo svaku kategoriju, tj. svaki box koji imamo

    public List<Stavke> dobaviOsn(){
        kategorija = "Osnovne Potrebe";
        List<Stavke> osnovneStavke = new ArrayList<>();
        osnovneStavke.add(new Stavke("Pasoš", kategorija, false));
        osnovneStavke.add(new Stavke("Lična karta", kategorija, false));
        osnovneStavke.add(new Stavke("Vozačka dozvola", kategorija, false));
        osnovneStavke.add(new Stavke("Karte", kategorija, false));
        osnovneStavke.add(new Stavke("Kišobran", kategorija, false));
        osnovneStavke.add(new Stavke("Novčanik", kategorija, false));
        osnovneStavke.add(new Stavke("Novac", kategorija, false));

        return osnovneStavke;

    }

    public List<Stavke> dobaviOdjeca(){
        String []stavke = {"Košulje", "Majice", "Jakna", "Duksevi", "Pantalone"};

        return popuniStavke(Konstanta.ODJECA, stavke);
    }
    public List<Stavke> dobaviHigijena(){
        String []stavke = {"Četkica za zube", "Pasta", "Konac za zube", "Sapun", "Umivalica", "Krema"};

        return popuniStavke(Konstanta.HIGIJENA, stavke);
    }
    public List<Stavke> dobaviZdravlje(){
        String []stavke = {"Tablete za bolove", "Tablete za temperaturu", "Flasteri", "Tablete za alergiju", "Vitamini"};

        return popuniStavke(Konstanta.ZDRAVLJE, stavke);
    }
    public List<Stavke> dobaviTehnologija(){
        String []stavke = {"Laptop", "Tablet", "Telefon", "Punjači", "Slušalice", "Kamera", "Bežični punjač", "Adapter"};

        return popuniStavke(Konstanta.TEHNOLOGIJA, stavke);
    }
    public List<Stavke> dobaviHrana(){
        String []stavke = {"Voda", "Sendviči za put", "Hljeb", "Suhomenstati proizvodi", "Sir"};

        return popuniStavke(Konstanta.HRANA, stavke);
    }

//    ova metoda dobija stavke i zatim ih iz liste popunjava na odgovarajuca mjesta
    public List<Stavke> popuniStavke(String kategorija, String[] stavke){
        List<String> lista = Arrays.asList(stavke);
        List<Stavke> listaStavki = new ArrayList<>();
        listaStavki.clear();

        for (int i = 0; i < lista.size(); i++){
            listaStavki.add(new Stavke(lista.get(i), kategorija, false));
        }
        return listaStavki;
    }

    // Dobavlja podatke za sve kategorije i vraća ih kao listu.
    public List<List<Stavke>> dobaviSvePodatke(){
        List<List<Stavke>> listaSvihPodataka = new ArrayList<>();
        listaSvihPodataka.clear();
        listaSvihPodataka.add(dobaviOsn());
        listaSvihPodataka.add(dobaviOdjeca());
        listaSvihPodataka.add(dobaviHigijena());
        listaSvihPodataka.add(dobaviZdravlje());
        listaSvihPodataka.add(dobaviTehnologija());
        listaSvihPodataka.add(dobaviHrana());
        return listaSvihPodataka;

    }

    public void cuvajPodatke(){
        List<List<Stavke>> listaSvihPodataka = dobaviSvePodatke();
        for (List<Stavke> lista: listaSvihPodataka){
            for (Stavke stavke:lista){
                bazapodataka.glDao().sacuvajPodatke(stavke);
            }
        }
        System.out.println("dodato");
    }

//    brisanje svih sistemskih podataka ili vracanje na pocetno stanje
    public void cuvajPodatkePoKategoriji(String kategorija, Boolean brisanje){
        try {
            List<Stavke> lista = obrisiIDobaviPoKategoriji(kategorija, brisanje);
            if (!brisanje){
                for (Stavke stavke : lista){
                    bazapodataka.glDao().sacuvajPodatke(stavke);
                }
                Toast.makeText(context, "Uspješno vraćeno", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Uspješno vraćeno", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
            Toast.makeText(context, "Greška", Toast.LENGTH_SHORT).show();
        }
    }

    // Briše podatke za kategoriju i vraća nove podatke za nju.
    private List<Stavke> obrisiIDobaviPoKategoriji(String kategorija, Boolean brisanje){
        if (brisanje){
            bazapodataka.glDao().obrisiSvePoKategorijiIDodato(kategorija, Konstanta.SYSTEM);
        }else {
            bazapodataka.glDao().obrisiSvePoKategoriji(kategorija);
        }
        switch (kategorija){
            case Konstanta.OSNOVNE_POTREBE:
                return dobaviOsn();

            case Konstanta.ODJECA:
                return dobaviOdjeca();

            case Konstanta.HIGIJENA:
                return dobaviHigijena();

            case Konstanta.ZDRAVLJE:
                return dobaviZdravlje();

            case Konstanta.TEHNOLOGIJA:
                return dobaviTehnologija();

            case Konstanta.HRANA:
                return dobaviHrana();

            default:
                return new ArrayList<>();
        }
    }
}
