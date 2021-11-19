package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

public class AjoutManuelDateActivity extends AppCompatActivity {
    //String codebarre;
    //String nomProduit;
    //String dateAjout;
    String datePeremption;
    private Aliments aliment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_manuel_date);

        Intent deScanActivity = getIntent();
        aliment = (Aliments)deScanActivity.getSerializableExtra("aliment");
    }
    //récupération de la date de péremption entrée par l'utilisateur et de la date d'ajout du produit, pour la transmettre à la BDD
    public void ajouterDatePeremption(View view) {

        //récupération de la date de péremption
        DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.datePicker1); // initiate a date picker
        String day = String.valueOf(simpleDatePicker.getDayOfMonth()); // get the selected day of the month
        String month = String.valueOf(1 + Integer.parseInt(String.valueOf(simpleDatePicker.getMonth()))); // get the selected month
        String year = String.valueOf(simpleDatePicker.getYear()); // get the selected year

        datePeremption= day + "-" +month + "-"+year;
        Log.i("fonctionnement normal", "date de péremption récupérée");

        aliment.setDate_peremption(datePeremption);
        Intent versAjoutAliment = new Intent();
        versAjoutAliment.setClass(this, AjoutAliment.class);
        versAjoutAliment.putExtra("aliment",aliment);
        startActivity(versAjoutAliment);
        finish();

    }
}
