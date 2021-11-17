package com.example.myfridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Notification_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        int duree;

        //ajouter date d'expiration
        String date_expi;
        date_expi="19-11-2021";
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_now = sdf(new Date());

        duree.printDifference(date_now,date_expi);
        notif (duree);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notif(int duree) {

        // création du channel pour envoyer les notifs
        CharSequence name = "Péremption";
        String description = "notification pour les péremptions proches";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("channel1", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        //création de la notif
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Savefood")
                .setContentText("Aliment blablabla va expirer dans tant de temps, veuillez le consommer rapidement.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(this);

        //ici on envoie la notif après un certain temps : duree
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationManager.notify(1, builder.build());
            }
        },duree);
    }
}