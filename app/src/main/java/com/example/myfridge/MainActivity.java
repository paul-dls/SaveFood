package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myfridge.ui.home.AjoutAlimentCodebarre;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private AlimentsOperations alimentsOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //affichage de la liste des aliments de la BDD dans le listView
        ListView listViewAliment = findViewById(R.id.listViewAliments);
        //création de l'objet permettant de modifier la BDD
        alimentsOperations = new AlimentsOperations(this);
        // On stocke dans le vecteur "lAlimentss" la liste des aliments
        // contenus dans la table "aliments" de la base de données
        Vector<Aliments> lAliments;
        alimentsOperations.open();
        lAliments = alimentsOperations.listAllAliments();
        alimentsOperations.close();

        // On associe au modèle de la ListView le vecteur de contacts
        // "lContacts"
        if (lAliments != null) {
            String[] anArrayString = new String[lAliments.size()];
            for (int i = 0; i < lAliments.size(); i++) {
                String s = lAliments.get(i).getId() + " - " +
                        lAliments.get(i).getNom_produit() + " : " +
                        lAliments.get(i).getDate_ajout();
                anArrayString[i] = s;
            }
            ArrayAdapter<String> listArrayAdapter =
                    new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1,
                            anArrayString);
            listViewAliment.setAdapter(listArrayAdapter);
        }


    }

    public void ajouter(View view) {
        Log.i("scan", "rentre dans le onClick");
        Intent VersAjoutAliment= new Intent();
        VersAjoutAliment.setClass(this, AjoutAlimentCodebarre.class);
        VersAjoutAliment.putExtra("message", "ok");
        startActivity(VersAjoutAliment);
        Log.i("scan", "intent vers AjoutAliment");
    }
}