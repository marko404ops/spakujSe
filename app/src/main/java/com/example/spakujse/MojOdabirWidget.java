package com.example.spakujse;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.spakujse.baza.RoomBaza;
import com.example.spakujse.model.Stavke;

import java.util.List;

public class MojOdabirWidget extends AppWidgetProvider {
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            // Ažuriraj widget izgled
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.moj_odabir_widget);
//
//            // Dobij listu odabranih stavki iz baze
//            RoomBaza baza = RoomBaza.getInstance(context);
//            List<Stavke> odabraneStavke = baza.glDao().dobaviSveSelektovano(true);
//
//            // Kreiraj string za prikaz stavki
//            StringBuilder stavkeString = new StringBuilder();
//            for (Stavke stavka : odabraneStavke) {
//                Log.d("stavka: ","("+stavka+")" );
//                stavkeString.append("• ").append(stavka.getNaziv()).append("\n");
//            }
//
//            // Postavi tekst na widget
//            views.setTextViewText(R.id.widgetTitle, "Odabrane stavke:");
//            views.setTextViewText(R.id.widgetListView, stavkeString.toString().trim());
//
//            // Dodaj klik na widget koji otvara aplikaciju
//            Intent intent = new Intent(context, SplashScreen.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//            views.setOnClickPendingIntent(R.id.widgetTitle, pendingIntent);
//
//            // Ažuriraj widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }
//    }
//}
@Override
public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.moj_odabir_widget);

        RoomBaza baza = RoomBaza.getInstance(context);
        List<Stavke> odabraneStavke = baza.glDao().dobaviSveSelektovano(true);

        StringBuilder stavkeString = new StringBuilder();
        for (Stavke stavka : odabraneStavke) {
            stavkeString.append("• ").append(stavka.getNaziv()).append("\n");
        }

        views.setTextViewText(R.id.widgetTitle, "Odabrane stavke:");
        views.setTextViewText(R.id.widgetListView, stavkeString.toString().trim());

        Intent intent = new Intent(context, SplashScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widgetTitle, pendingIntent);

        // Logovanje za proveru
        Log.d("WidgetUpdate", "Ažuriranje widgeta sa stavkama");
        Log.d("WidgetUpdate", "Broj odabranih stavki: " + odabraneStavke.size());
        Log.d("WidgetUpdate", "Stavke: " + stavkeString.toString());


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}}
