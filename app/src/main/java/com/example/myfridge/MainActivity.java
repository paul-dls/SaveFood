package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private AlimentsOperations alimentsOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void ajouter(View view) {
        // récupération date d'ajout
        Calendar calendrier=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM -yyyy");
        String dateAjout= df.format(calendrier.getTime());
        Log.i("fonctionnement normal", "date d'ajout récupérée");
        Log.i("scan", "rentre dans le onClick");
        Intent VersAjoutAliment= new Intent();
        VersAjoutAliment.setClass(this, AjoutAliment.class);
        VersAjoutAliment.putExtra("aliment", new Aliments("1","non communiqué",dateAjout,"non communiqué",1));
        finish();

        startActivity(VersAjoutAliment);
        Log.i("scan", "intent vers AjoutAliment");
    }
}