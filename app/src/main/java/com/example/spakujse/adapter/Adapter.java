package com.example.spakujse.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spakujse.CheckLista;
import com.example.spakujse.R;
import com.example.spakujse.konstante.Konstanta;

import java.util.List;

// Adapter klasa za RecyclerView koja omogućava prikaz liste kategorija sa slikama u okviru glavnog ekrana aplikacije.

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    List<String> naslovi;
    List<Integer> slike;
    LayoutInflater inflater;// Inflater za kreiranje izgleda stavki iz XML-a.
    Activity activity;// Aktivnost iz koje je adapter pozvan.

    public Adapter(Context context, List<String> naslovi, List<Integer> slike, Activity activity) {
        this.naslovi = naslovi;
        this.slike = slike;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kreira izgled za svaku stavku u RecyclerView-u koristeći "boksevi" layout.
        View view = inflater.inflate(R.layout.boksevi, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        // Proverava da li su pozicije unutar granica liste.
//        if (position < naslovi.size() && position < slike.size()) {
            holder.title.setText(naslovi.get(position));
            holder.img.setImageResource(slike.get(position));
            holder.LinearLayout.setAlpha(0.8F);

            // Klik događaj za svaku stavku u RecyclerView-u.
            holder.LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(activity, "Pritisnuto na boks", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), CheckLista.class);
                    intent.putExtra(Konstanta.HEADER_SMALL, naslovi.get(position));
                    if (Konstanta.MOJ_ODABIR.equals(naslovi.get(position))) {
                        // Ako je izabrana kategorija "Moj Odabir", sakriva stavke.
                        intent.putExtra(Konstanta.SHOW_SMALL, Konstanta.FALSE_STRING);
                    } else {
                        intent.putExtra(Konstanta.SHOW_SMALL, Konstanta.TRUE_STRING);
                    }
                    view.getContext().startActivity(intent);
                }
            });
//        }else {
//            throw new IndexOutOfBoundsException("Pozicija: " + position);
//        }
    }

    @Override
    public int getItemCount() {
        return Math.min(naslovi.size(), slike.size());
//        return naslovi.size();
//        ovoliko puta ce se renderovati adapter
    }

    // ViewHolder klasa za čuvanje referenci na UI komponente za pojedinačne stavke.
    public class viewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView img;
        LinearLayout LinearLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tittle);
            img=itemView.findViewById(R.id.img);
            LinearLayout=itemView.findViewById(R.id.LinearLayout);

        }
    }


}
