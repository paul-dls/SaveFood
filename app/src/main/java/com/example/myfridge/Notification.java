package com.example.myfridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Notification {

    private static Aliments aliment;


    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void envoyerNotif(Aliments aliment, Context mContext) {
        Log.i("normal paul date", "envoyerNotif appele");

        long duree;

        //ajouter date d'expiration
        Date date_expi;

        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_now = sdf.format(new Date());
        try{
            Date date_actuelle = sdf.parse(date_now);
            date_expi = sdf.parse(aliment.getDate_peremption());

            duree= Math.abs(date_expi.getTime() - date_actuelle.getTime());
            Log.i("normal paul date", "duree : "+ String.valueOf(duree));
            notif(duree + 3000,mContext,aliment);

        }catch(ParseException e){
            Log.i("erreur paul date",e.toString());
            duree = 0;
        }


    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    static private void notif(long duree, Context mContext,Aliments aliment) {
        Log.i("erreur paul notif", "notif est appellé");
        // création du channel pour envoyer les notifs
        CharSequence name = "Péremption";
        String description = "notification pour les péremptions proches";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("channel1", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        //création de la notif
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "channel1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Savefood")
                    .setContentText(String.format("%s va expirer dans tant de temps, veuillez le consommer rapidement.", aliment.getNom_produit()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(mContext);

            //ici on envoie la notif après un certain temps : duree
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationManager.notify(1, builder.build());
                }
            }, duree);
        }catch(Exception e){
            Log.i("erreur paul notif", e.toString());
        }
    }
}