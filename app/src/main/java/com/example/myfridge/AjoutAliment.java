package com.example.myfridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class AjoutAliment extends AppCompatActivity {
    Aliments aliment ;
    int quantite;
    String dateAjout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment);


        //récuparation codebarre aliment par scan
        Intent deScanActivity = getIntent();
        aliment = (Aliments) deScanActivity.getSerializableExtra("aliment");
        aliment.description();
    /*
        //récupération codebarre aliment manuelle
        Intent deAjoutManuelActivity = getIntent();
        aliment= (Aliments) deAjoutManuelActivity.getSerializableExtra("aliment");

     */
        //affichage contenu aliment
        TextView textNomProduit = findViewById(R.id.TextNomProduit);
        textNomProduit.setText(aliment.getNom_produit());
        TextView textDatePeremption = findViewById(R.id.TextDatePeremption);
        textDatePeremption.setText(aliment.getDate_peremption());

    }

    public void ScanCodebarre(View view) {

        Intent versScanActvity = new Intent();
        versScanActvity.setClass(this, ScannerCodeBarreActivity.class);
        versScanActvity.putExtra("aliment", (Serializable) aliment);
        startActivity(versScanActvity);
        Log.i("erreur intent paul","lancement scan code barre");
        finish();
    }

    public void EntreeCodeManuel(View view) {
        Intent versAjoutManuel = new Intent();
        versAjoutManuel.setClass(this, AjoutManuelCodeBarreActivity.class);
        versAjoutManuel.putExtra("aliment", (Serializable) aliment);
        startActivity(versAjoutManuel);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AjouterAlimentBDD(View view) {
        EditText editQuantite =findViewById(R.id.editTextQuantite);
        quantite = (int) Integer.parseInt(editQuantite.getText().toString());
        //Ajout de l'aliment dans la base de données
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        Log.i("getInfo","ouverture BDD");
        alimentsOperations.addAliments(aliment);
        Log.i("getInfo","ajout Aliment");
        alimentsOperations.close();
        Log.i("getInfo","fermeture BDD");

        Notification.envoyerNotif(aliment, this);

        Intent versMainActvity = new Intent();
        versMainActvity.setClass(this, MainActivity.class);
        //startActivity(versMainActvity);
        finish();
    }

    public void ScanDate(View view) {
        Intent versScannerDateActivity= new Intent();
        versScannerDateActivity.setClass(this, ScannerDateActivity.class);
        versScannerDateActivity.putExtra("aliment", (Serializable) aliment);
        startActivity(versScannerDateActivity);
        finish();
    }

    public void EntreeManuelleDate(View view) {
        Intent versgetInfo = new Intent();
        versgetInfo.setClass(this, AjoutManuelDateActivity.class);
        versgetInfo.putExtra("aliment", (Serializable) aliment);
        startActivity(versgetInfo);
        finish();
    }
}