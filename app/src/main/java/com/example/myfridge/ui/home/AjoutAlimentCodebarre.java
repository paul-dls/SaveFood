package com.example.myfridge.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myfridge.AjoutAlimentDate;
import com.example.myfridge.AjoutManuelActivity;
import com.example.myfridge.Aliments;
import com.example.myfridge.AlimentsOperations;
import com.example.myfridge.R;
import com.example.myfridge.ScanActivity;
import com.example.myfridge.ScannerDateActivity;
import com.example.myfridge.getInfoActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AjoutAlimentCodebarre extends AppCompatActivity {
    String codebarre;
    String datePeremption;
    String nomProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment);

        //récuparation codebarre par scan
        Intent deScanActivity = getIntent();
        codebarre=deScanActivity.getStringExtra("codebarre");
        nomProduit=deScanActivity.getStringExtra("NomProduit");

        //récupération codebarre manuelle
        Intent deAjoutManuelActivity = getIntent();
        codebarre=deAjoutManuelActivity.getStringExtra("codebarre");
        nomProduit=deAjoutManuelActivity.getStringExtra("NomProduit");

        //affichage des données pour validation par utilisateur ou modification
        TextView editNomProduit = findViewById(R.id.TextNomProduit);
        editNomProduit.setText(nomProduit);

    }

    public void AjouterProduit(View view) {
        //transfert vers l'activité pour récupérer les dates
        Intent versAjoutAlimentDate = new Intent();
        versAjoutAlimentDate.setClass(this, AjoutAlimentDate.class);
        versAjoutAlimentDate.putExtra("codebarre",codebarre);
        versAjoutAlimentDate.putExtra("NomProduit", nomProduit);
        startActivity(versAjoutAlimentDate);
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