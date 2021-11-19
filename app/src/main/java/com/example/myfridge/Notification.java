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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class Notification {


    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void Notifier(Context context){//envoyer toutes les notifs de peremptions proches
        try {

            //création de l'objet permettant de modifier la BDD
            AlimentsOperations alimentsOperations = new AlimentsOperations(context);
            // On stocke dans le vecteur "lAlimentss" la liste des aliments
            // contenus dans la table "aliments" de la base de données
            Vector<Aliments> lAliments;
            alimentsOperations.open();

            lAliments = alimentsOperations.listAllAliments();
            alimentsOperations.close();


            if (lAliments != null) {
                String[] anArrayString = new String[lAliments.size()];
                for (int i = 0; i < lAliments.size(); i++) {
                    String s = lAliments.get(i).getId() + " - " +
                            lAliments.get(i).getNom_produit() + " : " +
                            lAliments.get(i).getDate_ajout() + ":" +
                            lAliments.get(i).getDate_peremption() + ":" +
                            lAliments.get(i).getQuantite();
                    anArrayString[i] = s;
                }
                ArrayAdapter<String> listArrayAdapter =
                        new ArrayAdapter<String>(context,
                                android.R.layout.simple_list_item_1,
                                anArrayString);
                //on parcourt lAliments et on renvoie chaque aliment vers envoyerNotif
                for(Aliments aliment : lAliments){
                    envoyerNotif(aliment,context);
                }
            }
        }catch(Exception e){
            Log.i("erreur BDD paul", e.toString());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    static private void envoyerNotif(Aliments aliment, Context mContext) {// test pour savoir si l'aliment est bientot perimé
        Log.i("normal paul date", "envoyerNotif appele");

        long duree;
        Date date_expi;

        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_now = sdf.format(new Date());
        try{
            //on récupère la date actuelle
            Date date_actuelle = sdf.parse(date_now);
            date_expi = sdf.parse(aliment.getDate_peremption());

            //on rcalcule la duree restante
            duree= Math.abs(date_expi.getTime() - date_actuelle.getTime());
            Log.i("normal paul date", "duree : "+ String.valueOf(duree));

            //si la duree est trop faible (<1j) on envoie une notif
            if(duree <= 1000*3600*24) {
                notif(0, mContext, aliment);
            }
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
        NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        //création de la notif
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "channel1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Savefood")
                    .setContentText(String.format("%s va expirer demain, veuillez le consommer rapidement.", aliment.getNom_produit()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            //        .setSamallIcon(R.drawable.icone);
            NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(mContext);

            //ici on envoie la notif
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    //on créé un builder avec l'id du produit, ce qui peremt d'ecraser les notifications de ce produit si elles etaient encore la
                    try {
                        notificationManager.notify(Integer.parseInt(aliment.getId().substring(0,7)), builder.build());
                    }catch(Exception e){
                        Log.i("erreur paul notif",e.toString());
                    }
                }
            }, duree);
        }catch(Exception e){
            Log.i("erreur paul notif", e.toString());
        }
    }
}