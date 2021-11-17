package com.example.myfridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

public class Notification_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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

    }
}