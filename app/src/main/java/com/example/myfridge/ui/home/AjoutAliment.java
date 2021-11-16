package com.example.myfridge.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myfridge.AjoutManuelActivity;
import com.example.myfridge.Aliments;
import com.example.myfridge.AlimentsOperations;
import com.example.myfridge.R;
import com.example.myfridge.ScanActivity;
import com.example.myfridge.getInfoActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AjoutAliment extends AppCompatActivity {
    String codebarre;
    String dateAjout;
    String datePeremption;
    String nomProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment);

        // récupération date d'ajout
        Calendar calendrier=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM -yyyy");
        dateAjout= df.format(calendrier.getTime());
        Log.i("fonctionnement normal", "date d'ajout récupérée");

        Intent deScanActivity = getIntent();
        codebarre=deScanActivity.getStringExtra("codebarre");
        nomProduit=deScanActivity.getStringExtra("NomProduit");

        Intent deAjoutManuelActivity = getIntent();
        codebarre=deAjoutManuelActivity.getStringExtra("codebarre");
        nomProduit=deAjoutManuelActivity.getStringExtra("NomProduit");

        Intent degetInfo= getIntent();
        datePeremption=degetInfo.getStringExtra("DatePeremption");
    }

    public void AjouterProduit(View view) {

        //Ajout de l'aliment dans la base de données
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        Log.i("getInfo","ouverture BDD");
        alimentsOperations.addAliments(new Aliments(codebarre,nomProduit,dateAjout));
        Log.i("getInfo","ajout Aliment");
        alimentsOperations.close();
        Log.i("getInfo","fermeture BDD");
    }

    public void entreeDateManuel(View view) {
        Intent versgetInfo = new Intent();
        versgetInfo.setClass(this, getInfoActivity.class);
        startActivity(versgetInfo);
    }

    public void ScanDate(View view) {
    }

    public void EntreeCodeManuel(View view) {
        Intent versAjoutManuel = new Intent();
        versAjoutManuel.setClass(this, AjoutManuelActivity.class);
        startActivity(versAjoutManuel);
    }

    public void ScanCodebarre(View view) {
        Intent versScanActvity = new Intent();
        versScanActvity.setClass(this, ScanActivity.class);
        startActivity(versScanActvity);

    }
}