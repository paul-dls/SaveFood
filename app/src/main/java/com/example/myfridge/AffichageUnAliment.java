package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AffichageUnAliment extends AppCompatActivity {
    String codebarre;
    String dateAjout;
    String datePeremption;
    String urlImage;
    int quantite;
    String nomProduit;
    private AlimentsOperations alimentsOperations;
    TextView textquantite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_un_aliment);

        Intent deAffichageFrigo = getIntent();
        Aliments aliments = (Aliments) deAffichageFrigo.getSerializableExtra("alimentSelectionne");
        urlImage= deAffichageFrigo.getStringExtra("urlImage");
        codebarre=aliments.getId();
        datePeremption= aliments.getDate_peremption();
        dateAjout= aliments.getDate_ajout();
        nomProduit= aliments.getNom_produit();
        quantite= aliments.getQuantite();


        TextView infoProduit = findViewById(R.id.textProduit);
        infoProduit.setText("codebarre: " + codebarre +"\n"+
                            "nom du Produit: " + nomProduit +"\n"+
                            "date d'ajout: "+ dateAjout +"\n"+
                            "date de péremption: "+datePeremption);

        textquantite = findViewById(R.id.textQuantite);
        textquantite.setText( "quantité: "+ "\n" + String.valueOf(quantite));

        ImageView icon = findViewById(R.id.image_view);
        
    }

    //augmenter la quantité d'aliments déjà dans le frigo
    public void PlusQuantite(View view) {
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        alimentsOperations.ModifierQuantite(codebarre,quantite+1,  dateAjout, datePeremption, nomProduit);
        quantite= alimentsOperations.RetourneQuantite(codebarre,quantite);
        textquantite.setText( "quantité: "+ "\n" + String.valueOf(quantite));
        alimentsOperations.close();
    }
    //retirer des aliments
    public void MoinsQuantite(View view) {
        if(quantite>1) {
            AlimentsOperations alimentsOperations = new AlimentsOperations(this);
            alimentsOperations.open();
            alimentsOperations.ModifierQuantite(codebarre, quantite - 1,  dateAjout, datePeremption, nomProduit);
            quantite = alimentsOperations.RetourneQuantite(codebarre, quantite);
            textquantite.setText( "quantité: "+ "\n" + String.valueOf(quantite));
            alimentsOperations.close();
        }else{
            AlimentsOperations alimentsOperations = new AlimentsOperations(this);
            alimentsOperations.open();
            alimentsOperations.EffacerAliment(codebarre);
            alimentsOperations.close();
            Intent versMainActivity = new Intent();
            versMainActivity.setClass(this,MainActivity.class);
            startActivity(versMainActivity);
        }
    }

    //methode permettant d'"ffacer un aliment dans la BDD
    public void effacerAliment(View view) {
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        alimentsOperations.EffacerAliment(codebarre);
        alimentsOperations.close();
        Intent versMainActivity = new Intent();
        versMainActivity.setClass(this,MainActivity.class);
        startActivity(versMainActivity);
    }

    //méthode onClick pour retourner à l'accueil
    public void RetourHome(View view) {
        Intent versMainActivity= new Intent();
        versMainActivity.setClass(this, MainActivity.class);
        startActivity(versMainActivity);
    }
}