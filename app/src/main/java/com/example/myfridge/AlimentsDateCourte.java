package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.text.ParseException;



public class AlimentsDateCourte extends AppCompatActivity {
    Date dateActuelle;
    private AlimentsOperations alimentsOperations;
    String[] DatePeremptionAliments;
    String[] CodebarreAliments;
    String[] NomAliments;
    int[] QuantiteAliments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments_date_courte);


        // Définition des colonnes
// NB : SimpleCursorAdapter a besoin obligatoirement d'un ID nommé "_id"
        String[] columns = new String[] { "_id", "col1", "col2", "col3" };

// Définition des données du tableau
// les lignes ci-dessous ont pour seul but de simuler
// un objet de type Cursor pour le passer au SimpleCursorAdapter.
// Si vos données sont issues d'une base SQLite,
// utilisez votre "cursor" au lieu du "matrixCursor"
        MatrixCursor matrixCursor= new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object[] { 1,"nom de l'aliment","date de péremption","quantité" });
        ListView listeProduitDateCourte = findViewById(R.id.listeProduitDateCourte);

        try {
            //affichage de la liste des aliments de la BDD dans le listView

            //création de l'objet permettant de modifier la BDD
            alimentsOperations = new AlimentsOperations(this);
            // On stocke dans le vecteur "lAlimentss" la liste des aliments
            // contenus dans la table "aliments" de la base de données
            Vector<Aliments> lAliments;
            alimentsOperations.open();
            //alimentsOperations.vider();

            lAliments = alimentsOperations.listAllAliments();
            alimentsOperations.close();


            // On associe au modèle de la ListView le vecteur de contacts
            // "lContacts"
            if (lAliments != null) {
                NomAliments = new String[lAliments.size()];
                DatePeremptionAliments = new String[lAliments.size()];
                CodebarreAliments = new String[lAliments.size()];
                CodebarreAliments = new String[lAliments.size()];
                QuantiteAliments = new int[lAliments.size()];
                int j=0;
                for (int i = 0; i < lAliments.size(); i++) {
                    if(compareDate(lAliments.get(i).getDate_peremption())) {
                        NomAliments[j] = lAliments.get(i).getNom_produit();
                        DatePeremptionAliments[j] = lAliments.get(i).getDate_peremption();
                        CodebarreAliments[j] = lAliments.get(i).getId();
                        QuantiteAliments[j]= lAliments.get(i).getQuantite();
                        j++;
                        matrixCursor.addRow(new Object[] { i+2,lAliments.get(i).getNom_produit(), lAliments.get(i).getDate_peremption(),lAliments.get(i).getQuantite() });
                    }
                }
            }
        }catch(Exception e){
            Log.i("erreur BDD antonine", e.toString());
        }
        // on prendra les données des colonnes 1, 2 et 3...
        String[] from = new String[] {"col1", "col2", "col3"};

// ...pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[] { R.id.textViewNomAliment, R.id.textViewDatePeremption,R.id.textViewQuantite};

// création de l'objet SimpleCursorAdapter...
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_tableau_aliment_date_courte, matrixCursor, from, to, 0);

// ...qui va remplir l'objet ListView
        listeProduitDateCourte.setAdapter(adapter);
    }


    //comparaison de la date actuelle avec la date de péremption pour afficher les aliments se périmant dans moins de 3 jour
    public Boolean compareDate(String datePeremption){
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_now = sdf.format(new Date());
        Date date_expi;
        Long duree;
        Boolean result= false;
        try{
            Date date_actuelle = sdf.parse(date_now);
            date_expi = sdf.parse(datePeremption);
            duree= Math.abs(date_expi.getTime() - date_actuelle.getTime());
            Long dureeAttendu = Long.valueOf(3*24*60*60*1000);
            if (duree<dureeAttendu){
                result= true;
            }
        }catch(ParseException e){
            Log.i("erreur paul date",e.toString());
        }

        return result;
    }
}