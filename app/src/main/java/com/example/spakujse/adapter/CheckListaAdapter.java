package com.example.spakujse.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spakujse.R;
import com.example.spakujse.baza.RoomBaza;
import com.example.spakujse.konstante.Konstanta;
import com.example.spakujse.model.Stavke;

import java.util.List;

// Adapter za RecyclerView koji upravlja listom stavki
public class CheckListaAdapter extends RecyclerView.Adapter<CheckListaViewHolder>{

    Context context;
    List<Stavke> stavkeList;
    RoomBaza baza;
    String show;

    public CheckListaAdapter() {
    }

    public CheckListaAdapter(Context context, List<Stavke> stavkeList, RoomBaza baza, String show) {
        this.context = context;
        this.stavkeList = stavkeList;
        this.baza = baza;
        this.show = show;
        if(stavkeList.isEmpty())
            Toast.makeText(context.getApplicationContext(), "Trenutno nema dostupnih stavki", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CheckListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflater kreira prikaz za svaku stavku u RecyclerView-u
        return new CheckListaViewHolder(LayoutInflater.from(context).inflate(R.layout.check_lista_stavke,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListaViewHolder holder, int position) {
        // Postavljanje teksta i stanja checkbox-a za određenu stavku
        holder.checkBox.setText(stavkeList.get(position).getNaziv());
        holder.checkBox.setChecked(stavkeList.get(position).getIzabrano());

        // Prikaz ili sakrivanje dugmeta za brisanje i menjanje pozadine stavke
        if (Konstanta.FALSE_STRING.equals(show)){
            holder.btnDelete.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border1));

        }else {
            // Promena pozadine u zavisnosti od stanja stavke
            if (stavkeList.get(position).getIzabrano()){
                holder.layout.setBackgroundColor(Color.parseColor("#273C41"));
            }else {
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border1));
            }
        }

        // Postavljanje logike za klik na checkbox
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = holder.checkBox.isChecked();
                baza.glDao().odabir(stavkeList.get(position).getID(), check);
                if (Konstanta.FALSE_STRING.equals(show)){
                    stavkeList = baza.glDao().dobaviSveSelektovano(true);
                    notifyDataSetChanged();
                }
                else {
                    stavkeList.get(position).setIzabrano(check);
                    notifyDataSetChanged();

                    // Prikazivanje poruke o promeni stanja stavke
                    Toast toastPoruka = null;
                    if (toastPoruka != null){
                        toastPoruka.cancel();
                    }
                    if (stavkeList.get(position).getIzabrano()){
                        toastPoruka = Toast.makeText(context, "(" + holder.checkBox.getText()+") spakovano", Toast.LENGTH_SHORT);
                    }else {
                        toastPoruka = Toast.makeText(context, "(" + holder.checkBox.getText() + ") izvadjeno", Toast.LENGTH_SHORT);
                    }
                    toastPoruka.show();
                }
            }
        });

        // Postavljanje logike za klik na dugme za brisanje stavke
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Obrisati (" + stavkeList.get(position).getNaziv() + ")")
                        .setMessage("Da li ste sigurni da želite obrisati stavku?")
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                baza.glDao().obrisi(stavkeList.get(position));
                                stavkeList.remove(stavkeList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Otkaži", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Zatvaranje dijaloga bez akcije
                            }
                        }).setIcon(R.drawable.delete1)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stavkeList.size();
    }


}

// ViewHolder klasa za držanje referenci na elemente prikaza
class CheckListaViewHolder extends RecyclerView.ViewHolder{

    LinearLayout layout;
    CheckBox checkBox;
    Button btnDelete;
    public CheckListaViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.LinearLayout);
        checkBox = itemView.findViewById(R.id.checkbox);
        btnDelete = itemView.findViewById(R.id.delete);
    }
}