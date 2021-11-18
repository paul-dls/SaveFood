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
    String[] QuantiteAliments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_frigo);

        // récupération du nom et de la quantite des aliments à afficher dans le spinenr (menu déroulant)
        alimentsOperations = new AlimentsOperations(this);
        Vector<String[]> listeNomQuantite;
        alimentsOperations.open();
        listeNomQuantite = alimentsOperations.listeNomAliment();
        alimentsOperations.close();
        if (listeNomQuantite != null) {
            NomsAliments =new  String[listeNomQuantite.size()];
            QuantiteAliments= new String [listeNomQuantite.size()];
            for (int i = 0; i < listeNomQuantite.size(); i++) {
                String nom = listeNomQuantite.get(i)[0];
                String quantite = listeNomQuantite.get(i)[1];
                NomsAliments[i] = nom;
                QuantiteAliments[i] = quantite;
            }
        }

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        CustomAdapter aa = new CustomAdapter(this,NomsAliments,QuantiteAliments);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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