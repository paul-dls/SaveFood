package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AjoutAlimentDate extends AppCompatActivity {
    /*String codebarre;
    String dateAjout;
    String datePeremption;
    String nomProduit;
    int quantite;
    Aliments aliment= new Aliments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment_date);

        // récupération date d'ajout
        Calendar calendrier=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM -yyyy");
        dateAjout= df.format(calendrier.getTime());
        Log.i("fonctionnement normal", "date d'ajout récupérée");

        Intent deScanActivity = getIntent();
        aliment = (Aliments)deScanActivity.getSerializableExtra("aliment");
        Toast.makeText(this, nomProduit + "\n"+ codebarre, Toast.LENGTH_SHORT).show();

        //récupération date de péremption manuelle
        Intent degetInfo= getIntent();
        datePeremption=degetInfo.getStringExtra("DatePeremption");

        //récupération date de péremption par scan
        Intent deScannerDate= getIntent();
        datePeremption=deScannerDate.getStringExtra("DatePeremption");

        //affichage des données pour validation par utilisateur ou modification
        TextView textDatePeremption = findViewById(R.id.TextDatePeremption);
        textDatePeremption.setText(datePeremption);
    }

    public void ScanDate(View view) {
        Intent versScannerDateActivity= new Intent();
        versScannerDateActivity.setClass(this, ScannerDateActivity.class);
        startActivity(versScannerDateActivity);
    }

    public void EntreeManuelleDate(View view) {
        Intent versgetInfo = new Intent();
        versgetInfo.setClass(this, getInfoActivity.class);
        startActivity(versgetInfo);
    }

    public void AjouterAlimentBDD(View view) {
        EditText editQuantite =findViewById(R.id.editTextQuantite);
        quantite= Integer.parseInt(editQuantite.getText().toString());
        //Ajout de l'aliment dans la base de données
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        Log.i("getInfo","ouverture BDD");


        alimentsOperations.addAliments(new Aliments(codebarre,nomProduit,dateAjout,datePeremption,quantite));
        Log.i("getInfo","ajout Aliment");
        alimentsOperations.close();
        Log.i("getInfo","fermeture BDD");

        Intent versAjoutAlimentActivity = new Intent();
        versAjoutAlimentActivity.setClass(this, AjoutAliment.class);
        versAjoutAlimentActivity.putExtra("aliment",aliment);
        startActivity(versAjoutAlimentActivity);

    }*/
}