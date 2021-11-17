package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class AjoutAliment extends AppCompatActivity {
    Aliments aliment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment);

        //récuparation codebarre par scan
        Intent deScanActivity = getIntent();
        //aliment = deScanActivity.getSerializableExtra("aliment");
        // nomProduit=deScanActivity.getStringExtra("NomProduit");

        //récupération codebarre manuelle
        Intent deAjoutManuelActivity = getIntent();
        //aliment=deAjoutManuelActivity.getSerializableExtra("codebarre");
        // nomProduit=deAjoutManuelActivity.getStringExtra("NomProduit");
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
    }

    public void ScanDate(View view) {
    }

    public void EntreeManuelleDate(View view) {
    }
}