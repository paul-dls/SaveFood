package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Vector;

public class AffichageFrigoActivity extends AppCompatActivity implements AdapterView .OnItemSelectedListener{
    private AlimentsOperations alimentsOperations;
    String[] NomsAliments;
    int[] QuantiteAliments;
    String[] CodebarreAliments;
    String[] DateAjoutAliments;
    String[] DatePeremptionAliments;
    String[] AdresseImageAlimenst;
    Vector<Aliments> lAliments;
    int k =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_frigo);

        /*
        // récupération du nom et de la quantite des aliments à afficher dans le spinenr (menu déroulant)
        alimentsOperations = new AlimentsOperations(this);
        Vector<String[]> listeNomQuantiteCode;
        alimentsOperations.open();
        listeNomQuantiteCode = alimentsOperations.listeNomQuantiteCodebarre();
        alimentsOperations.close();
        if (listeNomQuantiteCode != null) {
            NomsAliments =new  String[listeNomQuantiteCode.size()];
            QuantiteAliments= new String [listeNomQuantiteCode.size()];
            CodebarreAliments =new String[listeNomQuantiteCode.size()];
            for (int i = 0; i < listeNomQuantiteCode.size(); i++) {
                String nom = listeNomQuantiteCode.get(i)[0];
                String quantite = listeNomQuantiteCode.get(i)[1];
                String codebarre = listeNomQuantiteCode.get(i)[2];
                NomsAliments[i] = nom;
                QuantiteAliments[i] = quantite;
                CodebarreAliments[i]= codebarre;
            }
        }

        */
        alimentsOperations = new AlimentsOperations(this);
        // On stocke dans le vecteur "lAlimentss" la liste des aliments
        // contenus dans la table "aliments" de la base de données
        alimentsOperations.open();
        //alimentsOperations.vider();

        lAliments = alimentsOperations.listAllAliments();
        alimentsOperations.close();

        // On associe au modèle de la ListView le vecteur de contacts
        // "lContacts"
        if (lAliments != null) {
            NomsAliments =new  String[lAliments.size()];
            QuantiteAliments= new int [lAliments.size()];
            CodebarreAliments =new String[lAliments.size()];
            DateAjoutAliments =new String[lAliments.size()];
            DatePeremptionAliments =new String[lAliments.size()];
            for (int i = 0; i < lAliments.size(); i++) {
                String nom = lAliments.get(i).getNom_produit();
                int quantite = lAliments.get(i).getQuantite();
                String codebarre = lAliments.get(i).getId();
                String dateAjout = lAliments.get(i).getDate_ajout();
                String datePeremption = lAliments.get(i).getDate_peremption();
                NomsAliments[i] = nom;
                QuantiteAliments[i] = quantite;
                CodebarreAliments[i] = codebarre;
                DateAjoutAliments[i] = dateAjout;
                DatePeremptionAliments[i] = datePeremption;

            }
        }


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        //CustomAdapter customAdapter = new CustomAdapter(this,NomsAliments,QuantiteAliments);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,NomsAliments);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if (k>0){
        Intent versAffichageUnAliment = new Intent();
        versAffichageUnAliment.setClass(this, AffichageUnAliment.class);
        versAffichageUnAliment.putExtra("alimentSelectionne", lAliments.get(position));
        startActivity(versAffichageUnAliment);}
        k++;
        Toast.makeText(getApplicationContext(), NomsAliments[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

        // TOD Auto-generated method stub

    }
}