package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_frigo);

        alimentsOperations = new AlimentsOperations(this);
        // On stocke dans le vecteur "lAlimentss" la liste des aliments
        // contenus dans la table "aliments" de la base de données
        Vector<String> listeNomAliments;
        alimentsOperations.open();
        listeNomAliments = alimentsOperations.listeNomAliment();
        alimentsOperations.close();
        if (listeNomAliments != null) {
            NomsAliments =new  String[listeNomAliments.size()];
            for (int i = 0; i < listeNomAliments.size(); i++) {
                String nom = listeNomAliments.get(i);
                NomsAliments[i] = nom;
            }
        }

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,NomsAliments);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), NomsAliments[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TOD Auto-generated method stub

    }
}