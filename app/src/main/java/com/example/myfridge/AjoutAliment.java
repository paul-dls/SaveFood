package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

public class AjoutAliment extends AppCompatActivity {
    Aliments aliment = new Aliments();
    int quantite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment);

        //récuparation codebarre aliment par scan
        Intent deScanActivity = getIntent();
        aliment = (Aliments) deScanActivity.getSerializableExtra("aliment");

        //récupération codebarre aliment manuelle
        Intent deAjoutManuelActivity = getIntent();
        aliment= (Aliments) deAjoutManuelActivity.getSerializableExtra("aliment");
    }

    public void ScanCodebarre(View view) {
        Intent versScanActvity = new Intent();
        versScanActvity.setClass(this, ScanActivity.class);
        versScanActvity.putExtra("aliment", (Serializable) aliment);
        startActivity(versScanActvity);
    }

    public void EntreeCodeManuel(View view) {
        Intent versAjoutManuel = new Intent();
        versAjoutManuel.setClass(this, AjoutManuelActivity.class);
        versAjoutManuel.putExtra("aliment", (Serializable) aliment);
        startActivity(versAjoutManuel);
    }

    public void AjouterAlimentBDD(View view) {
        EditText editQuantite =findViewById(R.id.editTextQuantite);
        quantite= Integer.parseInt(editQuantite.getText().toString());
        //Ajout de l'aliment dans la base de données
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        Log.i("getInfo","ouverture BDD");


        alimentsOperations.addAliments(aliment);
        Log.i("getInfo","ajout Aliment");
        alimentsOperations.close();
        Log.i("getInfo","fermeture BDD");

        Intent versMainActvity = new Intent();
        versMainActvity.setClass(this, MainActivity.class);
        startActivity(versMainActvity);
    }

    public void ScanDate(View view) {
        Intent versScannerDateActivity= new Intent();
        versScannerDateActivity.setClass(this, ScannerDateActivity.class);
        versScannerDateActivity.putExtra("aliment", (Serializable) aliment);
        startActivity(versScannerDateActivity);
    }

    public void EntreeManuelleDate(View view) {
        Intent versgetInfo = new Intent();
        versgetInfo.setClass(this, getInfoActivity.class);
        versgetInfo.putExtra("aliment", (Serializable) aliment);
        startActivity(versgetInfo);
    }
}